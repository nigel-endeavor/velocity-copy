package com.endeavorms.velocity.qto.fileimport;

import java.io.Serializable;

/**
 * DTO for file import queue messages.
 */
public record FileImportMessageDto(Long id, String type) implements Serializable {
}
