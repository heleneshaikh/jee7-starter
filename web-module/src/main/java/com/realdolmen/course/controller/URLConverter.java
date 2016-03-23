package com.realdolmen.course.controller;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.FacesBehavior;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Created by heleneshaikh on 07/03/16.
 */
@FacesConverter("URLconversion")
public class URLConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        URLData urlData = new URLData(component.toString());
        return urlData;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value.toString();
    }
}
