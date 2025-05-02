package com.example.demo.service;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.tools.FunctionDefinition;
import com.alibaba.dashscope.tools.ToolCallBase;
import com.alibaba.dashscope.tools.ToolCallFunction;
import com.alibaba.dashscope.tools.ToolFunction;
import com.alibaba.dashscope.utils.JsonUtils;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.victools.jsonschema.generator.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ChatService {

    private final Map<String, List<Message>> sessionMap = new HashMap<>();

    SchemaGeneratorConfigBuilder configBuilder = new SchemaGeneratorConfigBuilder(SchemaVersion.DRAFT_2020_12, OptionPreset.PLAIN_JSON);
    SchemaGeneratorConfig config = configBuilder
            .with(Option.EXTRA_OPEN_API_FORMAT_VALUES)
            .without(Option.FLATTENED_ENUMS_FROM_TOSTRING)
            .build();
    SchemaGenerator generator = new SchemaGenerator(config);
    ObjectNode jsonschemaGetTime = generator.generateSchema(GetTimeTool.class);

    FunctionDefinition fdGetTime = FunctionDefinition.builder()
            .name("get_time")
            .description("获取当前时间")
            .parameters(JsonUtils.parseString(jsonschemaGetTime.toString()).getAsJsonObject())
            .build();

    @Value("${dashscope.api-key}")
    private String apiKey;

    public String chat(String sessionId, String userInput) {
        try {
            List<Message> messages = sessionMap.computeIfAbsent(sessionId, k -> {
                List<Message> init = new ArrayList<>();
                init.add(createMessage(Role.SYSTEM, "You are a helpful assistant."));
                return init;
            });

            messages.add(createMessage(Role.USER, userInput));
            GenerationParam param = createGenerationParam(messages);
            GenerationResult result = new Generation().call(param);

            // 是否包含工具调用
            if (result.getOutput().getChoices().get(0).getMessage().getToolCalls() != null) {
                getToolCalls(result, messages);

                // 工具调用后最后一条是模型回复
                Message finalMsg = messages.get(messages.size() - 1);
                return finalMsg.getContent();
            } else {
                // 没有工具调用，直接返回回复
                String reply = result.getOutput().getChoices().get(0).getMessage().getContent();
                messages.add(createMessage(Role.ASSISTANT, reply));
                return reply;
            }

        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            e.printStackTrace();
            return "发生错误：" + e.getMessage();
        }
    }

    private void getToolCalls(GenerationResult result, List<Message> messages)
            throws NoApiKeyException, InputRequiredException {

        // 获取助手返回的消息（这时候应该有 tool_calls）
        Message assistantMessage = result.getOutput().getChoices().get(0).getMessage();

        // 添加助手返回的消息到上下文
        messages.add(assistantMessage);

        // 遍历所有工具调用，执行并添加 tool 消息
        for (ToolCallBase toolCall : assistantMessage.getToolCalls()) {
            if ("function".equals(toolCall.getType())) {
                ToolCallFunction toolFunc = (ToolCallFunction) toolCall;
                String functionName = toolFunc.getFunction().getName();
                String functionArgument = toolFunc.getFunction().getArguments();

                switch (functionName) {
                    case "get_time": {
                        // 获取当前时间工具
                        GetTimeTool tool = JsonUtils.fromJson(functionArgument, GetTimeTool.class);
                        String resultContent = tool.getCurrentTime();

                        // 创建工具调用的消息
                        Message toolMessage = Message.builder()
                                .role(Role.TOOL.getValue())  // 角色是 tool
                                .content(resultContent)
                                .toolCallId(toolCall.getId()) // 必须设置 toolCallId
                                .build();

                        // 将工具的结果消息添加到上下文
                        messages.add(toolMessage);
                        break;
                    }
                }
            }
        }

        // 添加完 tool 消息后，再次发送完整消息列表给模型进行回复
        GenerationParam param = createGenerationParam(messages);
        GenerationResult followupResult = new Generation().call(param);

        // 将模型的新回复添加到上下文
        Message finalReply = followupResult.getOutput().getChoices().get(0).getMessage();
        messages.add(finalReply);

        // 如果模型的回复还包含工具调用，则递归处理
        if (finalReply.getToolCalls() != null) {
            getToolCalls(followupResult, messages);
        }
    }

    private Message createMessage(Role role, String content) {
        return Message.builder().role(role.getValue()).content(content).build();
    }

    private GenerationParam createGenerationParam(List<Message> messages) {
        return GenerationParam.builder()
                .apiKey(apiKey)
                .model(Generation.Models.QWEN_PLUS)
                .enableSearch(true)
                .messages(messages)
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .topP(0.8)
                .tools(Collections.singletonList(ToolFunction.builder().function(fdGetTime).build()))
                .build();
    }

    public void clearSession(String sessionId) {
        sessionMap.remove(sessionId);
    }

    public static class GetTimeTool {
        public String getCurrentTime() {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return "当前时间：" + now.format(formatter) + "。";
        }
    }
}