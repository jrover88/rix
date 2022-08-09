package com.p27.rix.listener;

import com.p27.rix.request.CreditTransferRequest;
import com.p27.rix.response.CreditTransferResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@EnableRabbit
@AllArgsConstructor
public class RixListener {

    private AmqpTemplate template;

    @RabbitListener(queues = "${leg.two.maps.to.rix.listener}")
    public void handleInboundMessageFromMapsLegTwo(CreditTransferRequest request) {
        CreditTransferResponse response = new CreditTransferResponse(request.getMgsId());
        log.info("Message from LEG2.MAPS: " + request);
        template.convertAndSend("l3-rix-to-maps", response);
        log.info("LEG 3. Emit object from RIX to MAPS");
    }

}
