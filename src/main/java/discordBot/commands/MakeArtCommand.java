package discordBot.commands;

import apiClient.api.RequesterToYaART;
import apiClient.dto.DTO_ART;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.spec.MessageCreateFields;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Random;

public class MakeArtCommand implements SlashCommand {

    static RequesterToYaART requester = new RequesterToYaART();
    static Random rand = new Random();

    private static Mono<Message> methodThatTakesALongTime(ChatInputInteractionEvent event) {
        byte[] decodedImage;
        ByteArrayInputStream byteArrayInputStream;

        var message = event.getOption("message")
                .flatMap(it -> it.getValue())
                .map(it -> it.asString())
                .orElseThrow((() -> new IllegalArgumentException("empty message body")));

        try {
            decodedImage = requester.sendRequestToGenerateImage(DTO_ART.RequestArtDto.builder()
                    .messages(List.of(
                                    DTO_ART.MessagesArtDto.builder().text(message).build()
                            )
                    ).generation_options(DTO_ART.GenerationOptionsArtDto.builder()
                            .seed(rand.nextLong(Long.MAX_VALUE)).build())
                    .build());
            byteArrayInputStream = new ByteArrayInputStream(decodedImage);
            return event.editReply().withFiles(MessageCreateFields.File.of("image.jpeg", byteArrayInputStream));
        } catch (Exception e) {
            String error = e.toString().substring(e.toString().lastIndexOf("Exception: "));
            return event.editReply(message.concat("\nТы серьезно хотел это увидеть??? Наркоман? это же \n".concat(error)));
        }


    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
         /*
        Рабочий метод который берет картинку из интернета
        return event.reply(spec -> spec.addEmbed(embedCreateSpec -> embedCreateSpec.setImage("https://proslo.ru/wp-content/uploads/2024/02/china1.jpg")));
         */
        //Создаём ответ для демонстрации обработки
        event.deferReply().subscribe();
        return methodThatTakesALongTime(event).then();
    }
}
