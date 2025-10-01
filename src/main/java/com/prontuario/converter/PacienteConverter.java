package com.prontuario.converter;

import com.prontuario.entity.Paciente;
import com.prontuario.service.ProntuarioService;

import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.faces.convert.Converter;

@FacesConverter("pacienteConverter")
public class PacienteConverter implements Converter<Object> {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().isEmpty()) return null;
        ProntuarioService service = CDI.current().select(ProntuarioService.class).get();
        return service.findPacienteById(Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) return "";
        return String.valueOf(((Paciente) value).getId());
    }
}