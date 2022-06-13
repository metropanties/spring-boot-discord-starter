package me.metropanties.springdiscordstarter.listener.impl;

import lombok.RequiredArgsConstructor;
import me.metropanties.springdiscordstarter.listener.JDAListener;
import me.metropanties.springdiscordstarter.listener.ListenerManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ListenerManagerImpl implements ListenerManager {

    private final ApplicationContext context;
    private final JDA jda;
    private final Set<Object> registeredListeners = new HashSet<>();

    @PostConstruct
    public void init() {
        Map<String, Object> listenerBeans = context.getBeansWithAnnotation(JDAListener.class);
        if (listenerBeans.isEmpty())
            return;

        for (Object listener : listenerBeans.values()) {
            if (!(listener instanceof ListenerAdapter))
                continue;

            jda.addEventListener(listener);
            registeredListeners.add(listener);
        }
    }

    @Override
    public Collection<Object> getRegisteredListeners() {
        return registeredListeners.stream().toList();
    }

}
