package net.dusty_dusty.cts_compats.common.registry;

import net.minecraftforge.client.event.ModelEvent;

public interface IEmissiveRegistry {
    void onModelBake( ModelEvent.ModifyBakingResult event );

    void onRegisterAdditionalModels(ModelEvent.RegisterAdditional event);
}
