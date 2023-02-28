package com.example.application.views;

import com.example.application.model.dto.Item;
import com.example.application.model.response.ItemResponse;
import com.example.application.service.ItemService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Route(value = "list", layout = MainLayout.class)
@PageTitle("Item List")
public class ListView extends AppLayout  {
    public static final String NAME = "main";

    private final H1 viewTitle = new H1();
    private final ItemService itemService;

    private final Grid<ItemResponse> itemGrid = new Grid<>(ItemResponse.class);

    public ListView(@Autowired ItemService itemService) {
        this.itemService = itemService;
        createMainContent();
    }

    private void createMainContent() {
        setViewTitle("Items");

        Button addButton = new Button("Add Item");
        addButton.addClickListener(e -> showAddItemDialog());

        Button refreshButton = new Button("Refresh");
        refreshButton.addClickListener(e -> refreshGrid());

        HorizontalLayout buttonBar = new HorizontalLayout(addButton, refreshButton);
        buttonBar.setAlignItems(FlexComponent.Alignment.CENTER);
        buttonBar.setWidth("100%");

        itemGrid.setColumns("name", "description", "authorName", "releaseDate", "noOfCopy");
        itemGrid.setSizeFull();

        VerticalLayout content = new VerticalLayout(buttonBar, itemGrid);
        content.setSizeFull();
        content.expand(itemGrid);

        setContent(content);
        refreshGrid();
    }

    private void refreshGrid() {
        List<ItemResponse> items = itemService.getItems();
        ListDataProvider<ItemResponse> dataProvider = new ListDataProvider<>(items);
        itemGrid.setDataProvider(dataProvider);
    }

    private void setViewTitle(String title) {
        viewTitle.setText(title);
    }

    private void showAddItemDialog() {
        Dialog dialog = new Dialog();
        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);

        TextField name = new TextField("Name");
        TextField description = new TextField("Description");
        TextField authorName = new TextField("AuthorName");
        TextField noOfCopy = new TextField("Number Of Copy");
        DatePicker.DatePickerI18n singleFormatI18n = new DatePicker.DatePickerI18n();
        singleFormatI18n.setDateFormat("dd-MM-yyyy");

        DatePicker releaseDate = new DatePicker("Select a date:");
        releaseDate.setI18n(singleFormatI18n);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");


        Button cancel = new Button("Cancel", e -> dialog.close());
        Button save = new Button("Save", e -> {
            Item newItem = new Item();
            newItem.setName(name.getValue());
            newItem.setDescription(description.getValue());
            newItem.setAuthorName(authorName.getValue());
            newItem.setNoOfCopy(Integer.parseInt(noOfCopy.getValue()));
            newItem.setReleaseDate(releaseDate.getValue().format(dateFormatter));
            ItemResponse itemResponse = itemService.addItem(newItem);
            if(itemResponse == null) {
                Notification notification = new Notification(
                        "Error occurred during saving Item. Please try again later",
                        3000,
                        Notification.Position.TOP_END
                );
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.open();
            } else {
                refreshGrid();
                dialog.close();
            }
        });

        HorizontalLayout buttonBar = new HorizontalLayout(cancel, save);

        VerticalLayout layout = new VerticalLayout(name, description, authorName, noOfCopy, releaseDate, buttonBar);
        dialog.add(layout);

        dialog.open();
    }

}
