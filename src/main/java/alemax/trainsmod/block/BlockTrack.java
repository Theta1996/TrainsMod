package alemax.trainsmod.block;

import alemax.trainsmod.block.blockentity.BlockEntityTrackMarker;
import alemax.trainsmod.global.trackmarker.TrackMarker;
import alemax.trainsmod.global.trackmarker.TrackMarkerInstances;
import alemax.trainsmod.gui.ScreenTrackMarker;
import alemax.trainsmod.init.TMItemGroups;
import alemax.trainsmod.init.TMPackets;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelSet;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockTrack extends TMBlock {

    public BlockTrack() {
        super(Settings.of(Material.METAL), "track");
    }

    @Override
    public void onBlockRemoved(BlockState blockState_1, World world_1, BlockPos blockPos_1, BlockState blockState_2, boolean boolean_1) {
        super.onBlockRemoved(blockState_1, world_1, blockPos_1, blockState_2, boolean_1);
    }

    @Override
    public boolean isOpaque(BlockState blockState_1) {
        return false;
    }

    @Override
    public boolean isTranslucent(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1) {
        return true;
    }

    @Override
    public BlockRenderType getRenderType(BlockState blockState_1) {
        return BlockRenderType.INVISIBLE;
    }


    @Override
    public VoxelShape getCollisionShape(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, EntityContext entityContext_1) {
        return createCuboidShape(0,0,0,16, 4,16);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, EntityContext entityContext_1) {
        return getCollisionShape(blockState_1, blockView_1, blockPos_1, entityContext_1);
    }
}
