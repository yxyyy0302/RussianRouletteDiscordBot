package commands;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import russianroulettebot.Command;

import java.util.ArrayList;
import java.util.List;

public class Hello implements Command {
    @Override
    public String getName() {
        return "hello";
    }

    @Override
    public String getDescription() {
        return "Say hello to someone!";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> data = new ArrayList<>();
        data.add(new OptionData(OptionType.USER, "user", "user to send greetings to", true));
        return data;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        OptionMapping o1 = event.getOption("user");
        User user = o1.getAsUser();
        event.reply(event.getUser().getAsMention() + " says hello to " + user.getAsMention() + "!").queue();
    }
}

