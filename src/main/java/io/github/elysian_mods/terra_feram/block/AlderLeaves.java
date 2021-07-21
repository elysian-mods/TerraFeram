package io.github.elysian_mods.terra_feram.block;

public class AlderLeaves extends LeavesBlockWrapper {
    public AlderLeaves() {
        name = "alder_leaves";
        block = new AlderLeavesBlock();
    }

    public static class AlderLeavesBlock extends LeavesBlock {}
}
