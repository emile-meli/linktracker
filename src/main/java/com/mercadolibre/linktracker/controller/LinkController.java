package com.mercadolibre.linktracker.controller;

import com.mercadolibre.linktracker.dto.ExceptionDTO;
import com.mercadolibre.linktracker.dto.LinkDTO;
import com.mercadolibre.linktracker.dto.LinkResponseDTO;
import com.mercadolibre.linktracker.dto.MetricsDTO;
import com.mercadolibre.linktracker.exception.InvalidLink;
import com.mercadolibre.linktracker.exception.InvalidPassword;
import com.mercadolibre.linktracker.exception.LinkNotFound;
import com.mercadolibre.linktracker.service.LinkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class LinkController {
    
    private LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping("/link")
    public LinkResponseDTO createLink(@RequestBody LinkDTO linkDTO){
        return linkService.createLink(linkDTO);
    }

    @GetMapping("link/{id}")
    public RedirectView redirectLinkNoPass(@PathVariable int id){
        LinkResponseDTO linkResponseDTO = linkService.getLink(id,"");
        if(!linkResponseDTO.isValid()) throw new InvalidLink(linkResponseDTO.getUrl());
        return new RedirectView(linkResponseDTO.getUrl());
    }

    @GetMapping("link/{id}/{password}")
    public RedirectView redirectLink(@PathVariable int id, @PathVariable String password){
        LinkResponseDTO linkResponseDTO = linkService.getLink(id,password);
        if(!linkResponseDTO.isValid()) throw new InvalidLink(linkResponseDTO.getUrl());
        return new RedirectView(linkResponseDTO.getUrl());
    }

    @GetMapping("metrics/{id}")
    public MetricsDTO getMetrics(@PathVariable int id){
        return linkService.getLinkMetrics(id);
    }

    @GetMapping("invalidatelink/{id}")
    public LinkResponseDTO invalidateLink(@PathVariable int id){
        return linkService.invalidateLink(id);
    }

    @ExceptionHandler(InvalidLink.class)
    public ResponseEntity<ExceptionDTO> handleExceptionInvalidLink(InvalidLink exception){
        ExceptionDTO exceptionDTO = new ExceptionDTO();
        exceptionDTO.setName("Invalid Link");
        exceptionDTO.setDescription("The link " + exception.getMessage() + " is invalid");
        return new ResponseEntity<>(exceptionDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidPassword.class)
    public ResponseEntity<ExceptionDTO> handleExceptionInvalidPassword(InvalidPassword exception){
        ExceptionDTO exceptionDTO = new ExceptionDTO();
        exceptionDTO.setName("Wrong password");
        exceptionDTO.setDescription("A password is required or the entered password is incorrect.");
        return new ResponseEntity<>(exceptionDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LinkNotFound.class)
    public ResponseEntity<ExceptionDTO> handleExceptionLinkNotFound(LinkNotFound exception){
        ExceptionDTO exceptionDTO = new ExceptionDTO();
        exceptionDTO.setName("Link not found");
        exceptionDTO.setDescription("The link with id " + exception.getMessage() + " was not found.");
        return new ResponseEntity<>(exceptionDTO, HttpStatus.BAD_REQUEST);
    }

}
