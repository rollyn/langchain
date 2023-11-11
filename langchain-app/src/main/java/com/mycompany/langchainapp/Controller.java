package com.mycompany.langchainapp;

import com.mycompany.langchainapp.chatbot.CustomerSupportAgent;
import dev.langchain4j.chain.ConversationalChain;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.TokenWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static dev.langchain4j.model.openai.OpenAiModelName.GPT_3_5_TURBO;

@RestController
@RequestMapping("/api/v1")
public class Controller {

    ChatLanguageModel model = OpenAiChatModel.withApiKey(ApiKey.OPENAI_API_KEY);
    ChatMemory chatMemory = TokenWindowChatMemory.withMaxTokens(300, new OpenAiTokenizer(GPT_3_5_TURBO));

    @PostMapping("/chat")
    public String post(@RequestBody String msg) {
        String answer = model.generate(msg);
        return answer;
    }

    ConversationalChain chain = ConversationalChain.builder()
            .chatLanguageModel(OpenAiChatModel.withApiKey(ApiKey.OPENAI_API_KEY))
            .build();

    @PostMapping("/converse")
    public String converse(@RequestBody String msg) {
        String answer = chain.execute(msg);
        return answer;
    }

    @Autowired
    CustomerSupportAgent agent;

    @PostMapping("/chatsupport")
    public String chatsupport(@RequestBody String msg) {
        return interact(agent, msg);
    }


    String newLine = System.getProperty("line.separator");

    private String interact(CustomerSupportAgent agent, String userMessage) {
        String agentAnswer = agent.chat(userMessage);
        return String.join(newLine,
                "==========================================================================================",
                        "[User]:  "+userMessage,
                        "==========================================================================================",
                        "[Agent]:  "+agentAnswer,
                        "==========================================================================================");
    }
}
