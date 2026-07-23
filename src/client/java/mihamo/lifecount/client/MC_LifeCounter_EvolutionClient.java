package mihamo.lifecount.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.screen.ScreenEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.DrawContext;

public class MC_LifeCounter_EvolutionClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// Register HUD renderer for life display
		HudRenderCallback.EVENT.register(this::renderHud);
	}
	
	private void renderHud(DrawContext context, float tickDelta) {
		// HUD rendering will be implemented here
		// Currently placeholder for future enhancements
	}
}
