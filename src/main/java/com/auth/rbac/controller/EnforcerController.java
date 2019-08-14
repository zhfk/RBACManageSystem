package com.auth.rbac.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/enforce")
public class EnforcerController {
    private static final Logger logger = LoggerFactory.getLogger(EnforcerController.class);
}
