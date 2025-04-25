package by.rublevskaya.symbol;

import java.util.Map;

public interface OperatorConfig {
    Map<String, Integer> OPERATOR_PRECEDENCE = Map.of(
            "!", 3,    // NOT имеет наивысший приоритет
            "&", 2,    // AND
            "|", 1,    // OR
            "->", 0,   // импликация
            "~", 0     //эквивалентность
    );
}
