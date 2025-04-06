package com.example.demo.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Service
public class RssService {
    private static final String BASE_URL = "http://www.people.com.cn";

    public String fetchRssXml(String url) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().removeIf(c -> c instanceof StringHttpMessageConverter);
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));
        headers.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));

        ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.GET, new HttpEntity<>(headers), String.class
        );
        return response.getBody();
    }

    public JSONObject parseRssToJson(String xml) throws Exception {
        xml = preprocessXML(xml); // 先修复 XML
        Document doc = parseXml(xml);
        JSONObject json = new JSONObject();

        // Parse channel info
        json.put("title", getTagValue("title", doc.getDocumentElement()));
        json.put("description", getTagValue("description", doc.getDocumentElement()));
        json.put("link", getTagValue("link", doc.getDocumentElement()));

        // Parse items
        JSONArray items = new JSONArray();
        NodeList itemNodes = doc.getElementsByTagName("item");
        for (int i = 0; i < itemNodes.getLength(); i++) {
            Element item = (Element) itemNodes.item(i);
            JSONObject itemJson = new JSONObject();
            itemJson.put("title", getTagValue("title", item));
            itemJson.put("link", getTagValue("link", item));
            itemJson.put("description", processDescription(getTagValue("description", item)));
            items.put(itemJson);
        }
        json.put("items", items);

        return json;
    }
    private String preprocessXML(String xml) {
        return xml.replaceAll("<br>", "<br/>")
                .replaceAll("<br\\s+/>", "<br/>");
    }

    private Document parseXml(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
    }

    private String getTagValue(String tag, Element element) {
        NodeList nodes = element.getElementsByTagName(tag);
        return (nodes.getLength() > 0 && nodes.item(0).getFirstChild() != null)
                ? nodes.item(0).getTextContent().trim()
                : "";
    }

    private String processDescription(String desc) {
        return desc.replaceAll(
                "<img\\s+([^>]*?)src=[\"'](/[^\"']+)[\"']",
                "<img $1src=\"" + BASE_URL + "$2\""
        );
    }
}