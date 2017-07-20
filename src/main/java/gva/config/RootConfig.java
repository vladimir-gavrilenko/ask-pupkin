package gva.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@Import(PersistenceConfig.class)
@ComponentScan(basePackages = {"gva"},
        excludeFilters = {
            @Filter(type=FilterType.ANNOTATION, value=EnableWebMvc.class)
        }
)
public class RootConfig {

}
