package commands;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import russianroulettebot.Command;

import java.util.ArrayList;
import java.util.List;

public class Bully implements Command {
    public String getName() {
        return "bully";
    }

    @Override
    public String getDescription() {
        return "Bully Someone";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> data = new ArrayList<>();
        data.add(new OptionData(OptionType.USER, "user", "user you want to bully", true));
        return data;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        OptionMapping o1 = event.getOption("user");
        User user = o1.getAsUser();
        event.reply(user.getAsMention() + ", you got bullied by " + event.getUser().getAsMention()+ ". QAQ  >.<").queue();
    }
}
