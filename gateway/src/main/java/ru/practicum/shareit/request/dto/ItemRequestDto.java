package ru.practicum.shareit.request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.Marker;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequestDto {
    private Long id;

    @Size(min = 1, max = 64, groups = {Marker.Create.class, Marker.Update.class})
    @NotBlank(groups = Marker.Create.class)
    private String description;
}