package com.wizzer.mle.parts.roles;

import com.wizzer.mle.parts.j3d.props.I3dNodeTypeProperty;
import com.wizzer.mle.parts.j3d.roles.I3dRole;
import com.wizzer.mle.runtime.core.IMleRole;
import com.wizzer.mle.runtime.core.MleMediaRef;
import com.wizzer.mle.runtime.core.MleRole;
import com.wizzer.mle.runtime.core.MleRuntimeException;

import com.wizzer.mle.parts.mrefs.MleModelMediaRef;
import com.wizzer.mle.parts.j3d.min3d.Node;
import com.wizzer.mle.parts.j3d.min3d.Model;

/**
 * This class implements a carrier for propagating model properties
 * between Actors and Roles.
 *
 * @see Mle3dRole
 */
public class Mle3dModelCarrier
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
    public static final boolean set(IMleRole role, MleMediaRef reference)
            throws MleRuntimeException

    {
        Node root = null;
        boolean retValue = false;

        if ((role != null) && (role instanceof Mle3dRole)) {
            root = ((Mle3dRole) role).getRoot();
        } else {
            throw new MleRuntimeException("Mle3dModelCarrier: Invalid input arguments.");
        }

        if (root != null)
        {
            if (reference != null)
            {
                // Load media reference from Digital Playprint/Workprint.
                //MleModelMediaRef geometryMedia = (MleModelMediaRef)mlLoadMediaRef(reference, null);
                MleModelMediaRef geometryMedia = (MleModelMediaRef) reference;

                if (geometryMedia != null)
                {
                    // Read the model using the Media Reference.
                    Model model = geometryMedia.read();
                    root.setAsModel(model, (I3dRole) role);
                    root.init();
                    retValue = true;
                }
            } else {
                throw new MleRuntimeException("Mle3dModelCarrier: Invalid input arguments.");
            }

        }

        return retValue;
    }
}
