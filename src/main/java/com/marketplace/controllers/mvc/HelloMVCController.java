package com.marketplace.controllers.mvc;

import com.marketplace.services.GCPMetadataFetcherService;
import com.marketplace.utils.NetworkUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Matheus Alves
 */
@Controller
@RequestMapping("/")
public class HelloMVCController {


    private final GCPMetadataFetcherService service;

    public HelloMVCController(GCPMetadataFetcherService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String hello(HttpServletRequest httpServletRequest, Model model) {

        Map<String, String> metadata = new HashMap<>();

        metadata.put("clientIpAddress", httpServletRequest.getRemoteAddr());
        metadata.put("hostName", NetworkUtils.getHostAddress());
        metadata.putAll(service.getInstanceMetadata());

        model.addAttribute("metadata", metadata);

        return "hello";
    }

}
