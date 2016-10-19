package com.wizzer.mle.parts.roles;

import com.wizzer.mle.runtime.core.IMleRole;
import com.wizzer.mle.runtime.core.MleRole;
import com.wizzer.mle.parts.j3d.props.I3dNodeTypeProperty;
import com.wizzer.mle.parts.j3d.roles.I3dRole;
import com.wizzer.mle.parts.j3d.min3d.Node;
import com.wizzer.mle.runtime.core.MleRuntimeException;

/**
 * @brief 3d Node Type property carrier.
 *
 * This sets the node type on an Mle3dRole.
 *
 * @see Mle3dRole
 */
public class Mle3dNodeTypeCarrier
{
    public static boolean set(IMleRole role, I3dNodeTypeProperty.NodeType nodeType)
        throws MleRuntimeException
    {
        Node root = null;
        Node parent = null;
        boolean result = false;

        // Retrieve the min3d object.
        if ((role != null) && (role instanceof Mle3dRole)) {
            root = ((Mle3dRole) role).getRoot();
        } else {
            throw new MleRuntimeException("Mle3dNodeTypeCarrier: Invalid input arguments.");
        }

        if (root != null)
        {
            // See if we have already created this root node.
            switch (nodeType)
            {
                case TRANSFORM:
                    if (root.getNodeType() == I3dNodeTypeProperty.NodeType.TRANSFORM)
                        result = true;
                    break;
                case CAMERA:
                    if (root.getNodeType() == I3dNodeTypeProperty.NodeType.CAMERA)
                        result = true;
                    break;
                case LIGHT:
                    if (root.getNodeType() == I3dNodeTypeProperty.NodeType.LIGHT)
                        result = true;
                    break;
                case GEOMETRY:
                    if (root.getNodeType() == I3dNodeTypeProperty.NodeType.GEOMETRY)
                        result = true;
                    break;
                case BOUNDING_BOX:
                    if (root.getNodeType() == I3dNodeTypeProperty.NodeType.BOUNDING_BOX)
                        result = true;
                    break;
                case CLIPPING_PLANE:
                    if (root.getNodeType() == I3dNodeTypeProperty.NodeType.CLIPPING_PLANE)
                        result = true;
                    break;
                case NONE:
                    // ToDo: What behavior do we need to do here?
            }
        }

        // Retrieve parent of existing root, it may be null.
        int index = 0;
        if (result) {
            parent = root.getParent();
            if (parent != null) {
                index = parent.getChildIndexOf(root);
                parent.removeChildAt(index);
            }
        }

        if (! result)
        {
            // Create a min3d Node of the proper type.
            switch (nodeType)
            {
                case TRANSFORM:
                    root = new Node(I3dNodeTypeProperty.NodeType.TRANSFORM, (I3dRole)role);
                    break;
                case CAMERA:
                    root = new Node(I3dNodeTypeProperty.NodeType.CAMERA, null);
                    break;
                case LIGHT:
                    root = new Node(I3dNodeTypeProperty.NodeType.LIGHT, null);
                    break;
                case GEOMETRY:
                    root = new Node(I3dNodeTypeProperty.NodeType.GEOMETRY, (I3dRole)role);
                    break;
                case BOUNDING_BOX:
                    root = new Node(I3dNodeTypeProperty.NodeType.BOUNDING_BOX, null);
                    break;
                case CLIPPING_PLANE:
                    root = new Node(I3dNodeTypeProperty.NodeType.CLIPPING_PLANE, null);
                    break;
                default:
                    root = new Node(I3dNodeTypeProperty.NodeType.NONE, (I3dRole)role);
                    break;
            }

            // Assign the new min3d object to the Role.
            ((Mle3dRole)role).setRoot(root);

            result = true;
        }

        // Add to the parent if there was one before. Essentially we are either putting
        // back the found node of matching type or we are replacing the existing node
        // with one of the new type.
        if (parent != null) {
            root.setParent(parent);
            parent.addChildAt(root, index);
        }

        return result;
    }

    // ToDo: Not very Java like; get should probably return NodeType. But this might break
    // Carrier architecture - need to investigate.
    public static boolean get(MleRole role, I3dNodeTypeProperty.NodeType nodeType[])
        throws MleRuntimeException
    {
        Node root = null;
        boolean result = false;

        if ((role != null) && (role instanceof Mle3dRole)) {
            root = ((Mle3dRole) role).getRoot();
        } else {
            throw new MleRuntimeException("Mle3dNodeTpeCarrier: Invalid input arguments.");
        }

        if (root != null)
        {
            switch (root.getNodeType())
            {
                case TRANSFORM:
                    nodeType[0] = I3dNodeTypeProperty.NodeType.TRANSFORM;
                    break;
                case CAMERA:
                    nodeType[0] = I3dNodeTypeProperty.NodeType.CAMERA;
                    break;
                case LIGHT:
                    nodeType[0] = I3dNodeTypeProperty.NodeType.LIGHT;
                    break;
                case GEOMETRY:
                    nodeType[0] = I3dNodeTypeProperty.NodeType.GEOMETRY;
                    break;
                case BOUNDING_BOX:
                    nodeType[0] = I3dNodeTypeProperty.NodeType.BOUNDING_BOX;
                    break;
                case CLIPPING_PLANE:
                    nodeType[0] = I3dNodeTypeProperty.NodeType.CLIPPING_PLANE;
                    break;
                default:
                    nodeType[0] = I3dNodeTypeProperty.NodeType.NONE;
                    break;
            }
            result = true;
        }

        return result;
    }
}
