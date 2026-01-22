package com.diegodev.taskmanager.controllers.dtos.erros;

import java.util.List;

public record ErroResposta(int httpStatusValue, String msg, List<ErroCampo> erroCampos) {
}
