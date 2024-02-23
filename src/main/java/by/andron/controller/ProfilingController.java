package by.andron.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfilingController implements ProfilingControllerMBean {
    private boolean enabled = true;

}
