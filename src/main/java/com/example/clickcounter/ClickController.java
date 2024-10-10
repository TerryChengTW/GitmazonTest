// ClickController.java
package com.example.clickcounter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ClickController {
    private final AtomicLong clickCount = new AtomicLong(0);
    private long lastResetTime = System.currentTimeMillis();

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("clickCount", clickCount.get());
        return "index";
    }

    @PostMapping("/click")
    @ResponseBody
    public long incrementCount() {
        return clickCount.incrementAndGet();
    }

    @PostMapping("/reset")
    @ResponseBody
    public String resetCount() {
        long oldCount = clickCount.getAndSet(0);
        long currentTime = System.currentTimeMillis();
        long duration = (currentTime - lastResetTime) / 1000; // 轉換為秒
        lastResetTime = currentTime;
        return String.format("計數已重置。上一次計數: %d, 持續時間: %d 秒", oldCount, duration);
    }
}