package discordBot.commands;

import apiClient.api.RequesterToYaGPT;
import apiClient.dto.DTO_GPT;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.entity.Message;
import lombok.SneakyThrows;
import reactor.core.publisher.Mono;

import java.util.List;

public class RequestToGPTCommand implements SlashCommand {

    static RequesterToYaGPT requester = new RequesterToYaGPT();


    @SneakyThrows
    private static Mono<Message> methodThatTakesALongTime(ChatInputInteractionEvent event, String textMessage) {
        String responseMessage = requester.generateResponse(DTO_GPT.RequestGptDto.builder()
                .messages(List.of(
                        DTO_GPT.MessageGptDto.builder().role("system").text("Ты бездушный ИИ").build(),
                        DTO_GPT.MessageGptDto.builder().role("user").text(textMessage).build()
                )).build()
        );
        return event.editReply(STR."Запрос - \{textMessage} \n\n\{responseMessage}");
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        var message = event.getOption("message")
                .flatMap(it -> it.getValue())
                .map(it -> it.asString())
                .orElseThrow((() -> new IllegalArgumentException("empty message body")));
        event.deferReply().subscribe();
        return methodThatTakesALongTime(event, message).then();

    }
}
