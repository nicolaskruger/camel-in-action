package camelinaction.bindy.my;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@CsvRecord(separator = ",", crlf = "UNIX")
public class Cat {
    @DataField(pos = 1)
    private String name;
    @DataField(pos = 2)
    private String color;
    @DataField(pos = 3)
    private Integer age;
}
