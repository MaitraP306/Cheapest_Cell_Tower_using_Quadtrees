public class PointQuadtree {

    enum Quad {
        NW,
        NE,
        SW,
        SE
    }

    public PointQuadtreeNode root;

    public PointQuadtree() {
        this.root = null;
    }

    public boolean insert(CellTower a) {
        // System.out.println("insert");
        // System.out.println(a.x + " "+a.y+" "+a.cost);
        // TO be completed by students
        if(cellTowerAt(a.x , a.y)== true){
            return false;
        }
        if(root == null){
            root = new PointQuadtreeNode(a);
        }
        else{
            insert_traverse(root , a);
        }
        return true;
    }
    private void insert_traverse(PointQuadtreeNode node , CellTower a){
        if(node.celltower.x < a.x && node.celltower.y>= a.y){
            if(node.quadrants[3]== null){
                PointQuadtreeNode new_node = new PointQuadtreeNode(a);
                node.quadrants[3] = new_node;
            }
            else{
                insert_traverse(node.quadrants[3] , a);
            }
        }
        else if(node.celltower.x >= a.x && node.celltower.y>a.y){
            if(node.quadrants[2]== null){
                PointQuadtreeNode new_node = new PointQuadtreeNode(a);
                node.quadrants[2] = new_node;
            }
            else{
                insert_traverse(node.quadrants[2] , a);
            }
        }
        else if(node.celltower.x <= a.x && node.celltower.y<a.y){
            if(node.quadrants[1]== null){
                PointQuadtreeNode new_node = new PointQuadtreeNode(a);
                node.quadrants[1] = new_node;
            }
            else{
                insert_traverse(node.quadrants[1] , a);
            }
        }
        else if(node.celltower.x > a.x && node.celltower.y<=a.y){
            if(node.quadrants[0]== null){
                PointQuadtreeNode new_node = new PointQuadtreeNode(a);
                node.quadrants[0] = new_node;
            }
            else{
                insert_traverse(node.quadrants[0] , a);
            }
        }
    }

    public boolean cellTowerAt(int x, int y) {
        // System.out.println("search");
        // System.out.println(x + " "+y);
        // TO be completed by students
        if(root == null){
            return false;
        }
        return search_traverse(root , x , y);
    }
    private boolean search_traverse(PointQuadtreeNode node , int x ,int y){
        if(node.celltower.x == x && node.celltower.y == y){
            return true;
        }
        else if(node.celltower.x < x && node.celltower.y>= y){
            if(node.quadrants[3]== null){
                return false;
            }
            else{
                return search_traverse(node.quadrants[3] , x , y);
            }
        }
        else if(node.celltower.x >= x && node.celltower.y>y){
            if(node.quadrants[2]== null){
                return false;
            }
            else{
                return search_traverse(node.quadrants[2] , x , y);
            }
        }
        else if(node.celltower.x <= x && node.celltower.y<y){
            if(node.quadrants[1]== null){
                return false;
            }
            else{
                return search_traverse(node.quadrants[1] , x , y);
            }
        }
        else{
            if(node.quadrants[0]== null){
                return false;
            }
            else{
                return search_traverse(node.quadrants[0] , x , y);
            }
        }
    }
    public CellTower chooseCellTower(int x, int y, int r) {
        // TO be completed by students
        if(root == null){
            return null;
        }
        return min_cost_helper(x-r , x+r , y-r ,  y+r , root).celltower;
    }
    
    private PointQuadtreeNode min_cost_helper(int minx , int maxx , int miny , int maxy , PointQuadtreeNode node){
        //node andar hai

        PointQuadtreeNode temp = null;
        if(node == null){
            return null;
        }

        if(minx<= node.celltower.x && miny<= node.celltower.y){
            temp = return_min(temp , min_cost_helper(minx , maxx , miny , maxy , node.quadrants[2]));
        }
        if(minx<= node.celltower.x && maxy>= node.celltower.y){
            temp = return_min(temp , min_cost_helper(minx , maxx , miny , maxy , node.quadrants[0]));
        }
        if(maxx>= node.celltower.x && maxy>= node.celltower.y){
            temp = return_min(temp , min_cost_helper(minx , maxx , miny , maxy , node.quadrants[1]));
        }
        if(maxx>= node.celltower.x && miny<= node.celltower.y){
            temp = return_min(temp , min_cost_helper(minx , maxx , miny , maxy , node.quadrants[3]));
        }
        if(minx<= node.celltower.x && maxx >= node.celltower.x && miny <= node.celltower.y && maxy >= node.celltower.y){
            if(node.celltower.distance((minx+maxx)/2,(miny+maxy)/2)<= (maxx-minx)/2){temp = return_min(temp , node);}
        }
        return temp;
    }
    // private PointQuadtreeNode min_cost_helper(PointQuadtreeNode node , int x , int y , int r){
    //     if(node == null){
    //         return null;
    //     }
    //     PointQuadtreeNode temp = null;
    //     if(node.celltower.distance(x,y)<= r){
    //         temp = node;
    //     }
    //     // if(((x-r> node.celltower.x)||(x+r< node.celltower.x)) && ((y-r> node.celltower.y)||(y+r<node.celltower.y))){
    //     // }
    //     if((x-r>= node.celltower.x) && (y-r> node.celltower.y)){
    //         temp = return_min(temp , min_cost_helper(node.quadrants[1] , x , y ,r) , x ,y);
    //     }
    //     else if((x-r> node.celltower.x) && (y+r<=node.celltower.y)){
    //         temp = return_min(temp , min_cost_helper(node.quadrants[3] , x , y ,r), x , y);
    //     }
    //     else if((x+r< node.celltower.x) && (y-r>= node.celltower.y)){
    //         temp = return_min(temp , min_cost_helper(node.quadrants[0] , x , y ,r), x , y);
    //     }
    //     else if((x+r<= node.celltower.x) && (y+r<node.celltower.y)){
    //         temp = return_min(temp , min_cost_helper(node.quadrants[2] , x , y ,r), x , y);
    //     }
    //     else if(y-r>= node.celltower.y){
    //         temp = return_min(min_cost_helper(node.quadrants[0] , x , y , r) , min_cost_helper(node.quadrants[1] , x , y ,r), x , y);
    //         if(y-r == 0 && x> node.celltower.x){
    //             temp = return_min(temp , min_cost_helper(node.quadrants[3] , x , y , r) , x , y);
    //         }
    //     }
    //     else if(y+r<=node.celltower.y){
    //         temp = return_min(min_cost_helper(node.quadrants[2] , x , y , r) , min_cost_helper(node.quadrants[3] , x , y ,r), x , y);
    //         if(y+r == 0 && x< node.celltower.x){
    //             temp = return_min(temp , min_cost_helper(node.quadrants[0] , x , y , r) , x , y);
    //         }
    //     }
    //     else if(x-r>= node.celltower.x){
    //         temp = return_min(min_cost_helper(node.quadrants[1] , x , y , r) , min_cost_helper(node.quadrants[3] , x , y ,r), x , y);
    //         if(x-r == 0 && y< node.celltower.y){
    //             temp = return_min(temp , min_cost_helper(node.quadrants[2] , x , y , r) , x , y);
    //         }
    //     }
    //     else if(x+r<node.celltower.x){
    //         temp = return_min(min_cost_helper(node.quadrants[2] , x , y , r) , min_cost_helper(node.quadrants[0] , x , y ,r), x , y);
    //         if(x+r == 0 && y> node.celltower.y){
    //             temp = return_min(temp , min_cost_helper(node.quadrants[1] , x , y , r), x , y);
    //         }
    //     }
    //     else{
    //         if(x>node.celltower.x && y>node.celltower.y){
    //             temp = return_min(temp , min_cost_helper(node.quadrants[1] , x , y , r),x , y);
    //             temp = return_min(temp , min_cost_helper(node.quadrants[0] , x , y , r),x , y);
    //             temp = return_min(temp , min_cost_helper(node.quadrants[3] , x , y , r), x , y);
    //             if(node.celltower.distance(x , y)<r){
    //                 temp = return_min(temp , min_cost_helper(node.quadrants[2] , x , y , r), x , y);
    //             }
    //         }
    //         else if(x<node.celltower.x && y>node.celltower.y){
    //             temp = return_min(temp , min_cost_helper(node.quadrants[0] , x , y , r) , x , y);
    //             temp = return_min(temp , min_cost_helper(node.quadrants[1] , x , y , r) , x , y);
    //             temp = return_min(temp , min_cost_helper(node.quadrants[2] , x , y , r) , x , y);
    //             if(node.celltower.distance(x , y)<r){
    //                 temp = return_min(temp , min_cost_helper(node.quadrants[3] , x , y , r), x , y);
    //             }
    //         }
    //         else if(x<node.celltower.x && y<node.celltower.y){
    //             temp = return_min(temp , min_cost_helper(node.quadrants[2] , x , y , r) , x , y);
    //             temp = return_min(temp , min_cost_helper(node.quadrants[0] , x , y , r) , x , y);
    //             temp = return_min(temp , min_cost_helper(node.quadrants[3] , x , y , r) , x , y);
    //             if(node.celltower.distance(x , y)<r){
    //                 temp = return_min(temp , min_cost_helper(node.quadrants[1] , x , y , r), x , y);
    //             }
    //         }
    //         else if(x>node.celltower.x && y<node.celltower.y){
    //             temp = return_min(temp , min_cost_helper(node.quadrants[3] , x , y , r) , x , y);
    //             temp = return_min(temp , min_cost_helper(node.quadrants[1] , x , y , r) , x , y);
    //             temp = return_min(temp , min_cost_helper(node.quadrants[2] , x , y , r) , x , y);
    //             if(node.celltower.distance(x , y)<r){
    //                 temp = return_min(temp , min_cost_helper(node.quadrants[0] , x , y , r) , x , y);
    //             }
    //         }
    //     }
    //     return temp;
    // }
    private PointQuadtreeNode return_min(PointQuadtreeNode node1 , PointQuadtreeNode node2){
        if(node1== null){
            if(node2 == null){
                return null;
            }
            else{
                return node2;
            }
        }
        else{
            if(node2 == null){
                return node1;
            }
            else{
                if(node1.celltower.cost>node2.celltower.cost){
                    return node2;
                }
                else{
                    return node1;
                }
            }
        }
    }

}
