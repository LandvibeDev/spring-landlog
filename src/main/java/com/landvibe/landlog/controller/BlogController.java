package com.landvibe.landlog.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

public interface BlogController {
	String blog(@RequestParam Long creatorId, Model model);
}
