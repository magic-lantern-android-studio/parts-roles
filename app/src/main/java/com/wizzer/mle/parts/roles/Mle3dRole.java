package com.wizzer.mle.parts.roles;

import java.util.Vector;

import com.wizzer.mle.runtime.core.MleActor;
import com.wizzer.mle.runtime.core.MleRole;
import com.wizzer.mle.parts.j3d.roles.I3dRole;
import com.wizzer.mle.parts.j3d.props.I3dNodeTypeProperty;
import com.wizzer.mle.parts.j3d.min3d.Node;

/**
 * Created by msm on 8/16/16.
 */
public class Mle3dRole extends MleRole implements I3dRole
{
    protected I3dNodeTypeProperty.NodeType m_nodeType;
    protected Node m_root;

    private Vector<MleRole> m_children;

    public Mle3dRole(MleActor actor, I3dNodeTypeProperty.NodeType nodeType)
    {
        super(actor);

        if (nodeType == null)
            m_nodeType = I3dNodeTypeProperty.NodeType.NONE;
        else
            m_nodeType = nodeType;
        m_root = null;
        m_children = new Vector<>();
    }

    @Override
    public void init()
    {
        // Create a Node of type m_nodeType.
        m_root = new Node(m_nodeType, this);
    }

    @Override
    public void dispose()
    {
        if (m_root != null) {
            // Remove it from its parent.
            Node parent = m_root.getParent();
            if (parent != null) {
                parent.removeChild(m_root);
                m_root.setParent(null);
            }

            // Release Node resources;
            m_root.clear();

            // Remove reference back to Role.
            m_root.setRole(null);
        }
        m_nodeType = I3dNodeTypeProperty.NodeType.NONE;
        m_root = null;
    }

    @Override
    public void addChild(MleRole child)
    {
        if ((child != null) && (child instanceof Mle3dRole))
            m_children.add(child);
        // ToDo: what happens if child is not a 3d Role?
    }

    public void removeChild(MleRole child)
    {
        if ((child != null) && (child instanceof Mle3dRole)) {
            m_children.remove(child);
        }
    }

    public MleRole getChildAt(int i)
    {
        return m_children.get(i);
    }

    public void clearChildren()
    {
        m_children.clear();
    }

    public int numChildren() { return m_children.size(); }

    public void setNodeType(I3dNodeTypeProperty.NodeType nodeType)
    {
        m_nodeType = nodeType;
    }

    public I3dNodeTypeProperty.NodeType getNodeType() { return m_nodeType; }

    public void setRoot(Node root) { m_root = root; }

    public Node getRoot() { return m_root; }
}
