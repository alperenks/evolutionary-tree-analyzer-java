import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    public int nodeId;
    public String nodeName;
    public int childCount;
    public boolean isLeaf;
    public String tolOrgLink;
    public boolean isExtinct;
    public int confidence;
    public int phylesis;
    public TreeNode parent;
    public List<TreeNode> children;

    public TreeNode(int nodeId, String nodeName, int childCount, boolean isLeaf, String tolOrgLink, boolean isExtinct, int confidence, int phylesis) {
        this.nodeId = nodeId;
        this.nodeName = nodeName;
        this.childCount = childCount;
        this.isLeaf = isLeaf;
        this.tolOrgLink = tolOrgLink;
        this.isExtinct = isExtinct;
        this.confidence = confidence;
        this.phylesis = phylesis;
        this.children = new ArrayList<>();
    }
}
