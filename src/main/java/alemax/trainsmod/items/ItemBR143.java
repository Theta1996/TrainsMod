package alemax.trainsmod.items;

import java.util.List;

import alemax.trainsmod.entities.EntityBR143;
import alemax.trainsmod.entities.EntityRailcar;
import alemax.trainsmod.init.ModBlocks;
import alemax.trainsmod.init.ModItems;
import alemax.trainsmod.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import trackapi.lib.ITrack;
import trackapi.lib.ITrackBlock;

public class ItemBR143 extends Item {
	
	public ItemBR143() {
		setRegistryName("item_br143");
		setUnlocalizedName("item_br143");
		setCreativeTab(CommonProxy.tab_trainsmod);
		
		ModItems.ITEMS.add(this);
	}
		
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			if((worldIn.getBlockState(pos).getBlock() instanceof ITrackBlock || worldIn.getTileEntity(pos) instanceof ITrack) && !(worldIn.getBlockState(pos).getBlock().getRegistryName().equals(ModBlocks.AM_RAIL_CURVED.getRegistryName()))) {
				double posX = pos.getX() + 0.5;
				double posY = pos.getY() + 0.125;
				double posZ = pos.getZ() + 0.5;
				EntityBR143 subway = new EntityBR143(worldIn, pos);
				AxisAlignedBB subwayBox = subway.getBoundBox().offset(subway.posX, subway.posY, subway.posZ);
				List<EntityRailcar> trains = worldIn.getEntitiesWithinAABB(EntityRailcar.class, new AxisAlignedBB(-30 + subwayBox.minX, -10 + subwayBox.minY, -30 + subwayBox.minZ, 30 + subwayBox.maxX, 10 + subwayBox.maxY, 30 + subwayBox.maxZ)); 
				boolean intersect = false;
				for(EntityRailcar e : trains) {
					if(e.getEntityBoundingBox().intersects(subwayBox)) {
						intersect = true;
						break;
					}
				}
				if(!intersect) {
					worldIn.spawnEntity(subway);
				}
			}
		}
			
		
		return EnumActionResult.SUCCESS;
	}
	
}