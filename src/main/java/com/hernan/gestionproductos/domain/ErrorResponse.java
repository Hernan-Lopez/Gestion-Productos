package com.hernan.gestionproductos.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Detalles de un error producido en la aplicación")
public class ErrorResponse {
    @Schema(description = "Código HTTP del error", example = "400")
    private int code;

    @Schema(description = "Mensaje corto del error", example = "Bad Request")
    private String message;

    @Schema(description = "Nivel del error", example = "Error")
    private String level;

    @Schema(description = "Descripción detallada del error", example = "Detalles adicionales del error")
    private String description;

    public ErrorResponse(int code, String message, String level, String description) {
        this.code = code;
        this.message = message;
        this.level = level;
        this.description = description;
    }

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
