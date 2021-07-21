package io.github.elysian_mods.terra_feram.block;

public class AlderLog extends LogBlockWrapper {
    public AlderLog() {
        name = "alder_log";
        block = new AlderLogBlock();
    }

    public static class AlderLogBlock extends LogBlock {}
}
