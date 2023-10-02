package me.kevin.customerapi.validation;


import jakarta.validation.GroupSequence;
import me.kevin.customerapi.validation.group.MinMaxGroup;
import me.kevin.customerapi.validation.group.NotEmptyGroup;
import me.kevin.customerapi.validation.group.PatternCheckGroup;
import me.kevin.customerapi.validation.group.PositiveGroup;

@GroupSequence({NotEmptyGroup.class, PositiveGroup.class, MinMaxGroup.class, PatternCheckGroup.class})
public interface ValidationSequence {
}
