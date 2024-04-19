package apiClient.utils;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:local.properties",
        "classpath:application.properties"
})
public interface Properties extends Config {
    Properties properties = ConfigFactory.create(Properties.class);

    @Key("discord.bot.token")
    String discordToken();

    @Key("yandex.oauth.token")
    String yaGptOauthToken();

    @Key("yandex.catalog.id")
    String yaCatalogId();

    @Key("yandex.gpt.url")
    String yandexGptUrl();

    @Key("iam.token")
    String iamToken();

    @Key("yandex.art.url")
    String yandexArtUrl();


    @Key("yandex.get.art.url")
    String yandexGetArtUrl();

}