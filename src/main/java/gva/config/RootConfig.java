package gva.config;

import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;

@Configuration
@Import({PersistenceConfig.class, SecurityConfig.class})
@ComponentScan(basePackages = {"gva"},
        excludeFilters = {
            @Filter(type=FilterType.ANNOTATION, value=Configuration.class)
        }
)
@PropertySource("classpath:application.properties")
public class RootConfig {

}
