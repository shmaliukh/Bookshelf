package com.vshmaliukh.httpclientmodule.http_client_services.rest_template_client_service;

import com.vshmaliukh.httpclientmodule.http_client_services.AbstractSpringClientService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@NoArgsConstructor
public class RestTemplateClientServiceImp extends AbstractSpringClientService {

    @Autowired
    public RestTemplateClientServiceImp(ShelfRestTemplateClientImp shelfRestTemplateClientImp) {
        this.shelfClientImp = shelfRestTemplateClientImp;
    }

}
