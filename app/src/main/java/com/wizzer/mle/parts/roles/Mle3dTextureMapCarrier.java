package com.wizzer.mle.parts.roles;

import com.wizzer.mle.parts.j3d.min3d.Node;
import com.wizzer.mle.parts.j3d.min3d.TextureMap;
import com.wizzer.mle.parts.mrefs.MleTextureMapMediaRef;
import com.wizzer.mle.runtime.core.MleMediaRef;
import com.wizzer.mle.runtime.core.MleRole;
import com.wizzer.mle.runtime.core.MleRuntimeException;

/**
 * Created by msm on 8/26/16.
 */
public class Mle3dTextureMapCarrier
{
    /**
     * Read the model from the specified Media Reference and update
     * the specified Role.
     *
     * @param role The Role to update.
     * @param reference The Media Reference to obtain the model from.
     *
     * @return If the model is successfully set on the Role, then
     * <b>true</b> will be returned. Otherwise, <b>false</b> will
     * be returned.
     *
     * @throws MleRuntimeException This exception is thrown if
     * the specified parameters are <b>null</b>. It will also be
     * thrown if an error occurs while setting the property.
     */
    public static final boolean set(MleRole role, MleMediaRef reference)
            throws MleRuntimeException

    {
        Node root = null;
        boolean retValue = false;

        if ((role != null) && (role instanceof Mle3dRole)) {
            root = ((Mle3dRole) role).getRoot();
        } else {
            throw new MleRuntimeException("Mle3dTextureMapCarrier: Invalid input arguments.");
        }

        if (root != null)
        {
            if (reference != null)
            {
                // Load media reference from Digital Playprint/Workprint.
                //MleTextureMapMediaRef textureMedia = (MleTextureMapMediaRef)mlLoadMediaRef(reference, null);
                MleTextureMapMediaRef textureMedia = (MleTextureMapMediaRef) reference;

                if (textureMedia != null)
                {
                    // Read the texture map using the Media Reference.
                    TextureMap textureMap = textureMedia.read();
                    root.setTextures(textureMap);
                    retValue = true;
                }
            } else {
                throw new MleRuntimeException("Mle3dTextureMapCarrier: Invalid input arguments.");
            }

        }

        return retValue;
    }
}
