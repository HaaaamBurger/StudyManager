package org.studymanager.main.common.config;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.NullValueCheckStrategy;

/**
 * Configuration for MapStruct mappers.
 *
 * <p>Defines the following settings:
 * - `componentModel`: Specifies the DI framework integration (e.g., Spring).
 * - `injectionStrategy`: Determines how dependencies are injected into the
 * mapper (e.g., constructor-based injection).
 * - `nullValueCheckStrategy`: Ensures null checks are always performed before mapping.
 * - `implementationPackage`: Specifies the package for the generated mapper implementations.
 *
 * @author Melnyk Oleksandr
 * @version 1.0
 */
@org.mapstruct.MapperConfig(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        implementationPackage = "<PACKAGE_NAME>.impl"
)
public interface MapperConfig {
}
