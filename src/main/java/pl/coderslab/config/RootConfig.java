package pl.coderslab.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import({WebConfig.class, DataConfig.class})
public class RootConfig {
}
