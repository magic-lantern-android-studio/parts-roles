package com.wizzer.mle.parts.roles;

import java.util.Vector;

import com.wizzer.mle.parts.j3d.roles.I3dRole;
import com.wizzer.mle.runtime.core.MleActor;
import com.wizzer.mle.runtime.core.MleRole;
import com.wizzer.mle.parts.j3d.props.I3dNodeTypeProperty;
import com.wizzer.mle.parts.j3d.min3d.Node;

/**
 * Created by msm on 8/16/16.
 */
public class Mle3dRole extends MleRole implements I3dRole
{
    I3dNodeTypeProperty.NodeType m_nodeType;
    Node m_root;

    private Vector<MleRole> m_children;

    public Mle3dRole(MleActor actor, I3dNodeTypeProperty.NodeType nodeType)
    {
        super(actor);

        if (nodeType == null)
            m_nodeType = I3dNodeTypeProperty.NodeType.TRANSFORM;
        m_children = new Vector<>();
    }

    @Override
    public void init()
    {
    }

    @Override
    public void dispose()
    {
    }

    @Override
    protected void addChild(MleRole child)
    {
        // ToDo: Validate that child is an instance of Mle3dRole.
        m_children.add(child);
    }

    public void setNodeType(I3dNodeTypeProperty.NodeType nodeType)
    {
        m_nodeType = nodeType;
    }

    public I3dNodeTypeProperty.NodeType getNodeType() { return m_nodeType; }

    public void setRoot(Node root) { m_root = root; }

    public Node getRoot() { return m_root; }
}
