package com.example.backend.controller;
import com.example.backend.dto.HintRequest;
import com.example.backend.dto.HintResponse;
import com.example.backend.service.HintService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hints")
@CrossOrigin(origins = "*")
public class HintController {

    private final HintService hintService;

    public HintController(HintService hintService) {
        this.hintService = hintService;
    }

    @PostMapping
    public HintResponse getHint(@RequestBody HintRequest request) {
        String hint = hintService.generateHint(request);
        return new HintResponse(hint);
    }
}