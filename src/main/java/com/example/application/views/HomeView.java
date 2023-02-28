package com.example.application.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.Random;

@Route(value = "", layout = MainLayout.class)
public class HomeView extends VerticalLayout {

    private final Image image;

    public HomeView() {
        // Create an Image component and set its source to a random image URL
        image = new Image();
        image.setAlt("Random image");
        image.setSrc(getRandomImageUrl());

        // Add the Image component to the view
        add(image);

        // Refresh the image every 5 seconds
        setSpacing(false);
        setWidthFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setFlexGrow(1, image);
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                UI.getCurrent().access(() -> {
                    image.setSrc(getRandomImageUrl());
                });
            }
        }).start();
    }

    private String getRandomImageUrl() {
        // Generate a random image URL using the Lorem Picsum API
        return "https://picsum.photos/800/600?random=" + new Random().nextInt();
    }
}

