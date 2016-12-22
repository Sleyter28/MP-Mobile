<?php 
$response = [];
if (!empty($_POST)) { 
    $username = "ReadApp"; 
    $password = "MarketPymesApp"; 
    $servername = "localhost";
    $dbname = $_POST['DB_name'];
    try {
        $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
        $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        $sql1 = $conn->prepare("select lp_productos_codigo_i092002992.cp_producto_codigo_92939898234, lp_categorias_nombre_i092002992.cp_categorias_nombre_92939898234,
        lp_producto_descripcion_i092002992.cp_producto_descripcion_92939898234, lp_inventario_matriz_i092002992.cp_inventario_matriz_cantidad_919819828828,
        lp_productos_limite_existencia_i092002992.cp_producto_limite_92939898234
        from 
        lp_productos_i092002992 
        INNER JOIN lp_productos_codigo_i092002992
        INNER JOIN lp_productos_categoria_i092002992
        INNER JOIN lp_categorias_nombre_i092002992
        INNER JOIN  lp_producto_descripcion_i092002992 
        INNER JOIN  lp_productos_limite_existencia_i092002992 
        INNER JOIN  lp_inventario_matriz_i092002992        
        ON lp_productos_codigo_i092002992.cp_id_producto_919819828828 = lp_productos_i092002992.cp_id_producto_919819828828 and
        lp_producto_descripcion_i092002992.cp_id_producto_919819828828=lp_productos_i092002992.cp_id_producto_919819828828 and
        lp_productos_limite_existencia_i092002992.cp_id_producto_919819828828=lp_productos_i092002992.cp_id_producto_919819828828 and
        lp_inventario_matriz_i092002992.cp_id_producto_919819828828 = lp_productos_i092002992.cp_id_producto_919819828828 and
        lp_productos_categoria_i092002992.cp_id_producto_919819828828 = lp_productos_i092002992.cp_id_producto_919819828828 and
        lp_categorias_nombre_i092002992.cp_id_categoria_919819828828 = lp_productos_categoria_i092002992.cp_id_categoria_919819828828
        where lp_productos_i092002992.cp_id_company_919819828828 = :id_company and 
        lp_productos_i092002992.cp_activo_92982992 = 1");
        $sql1->execute(array('id_company' => $_POST['id_company']));
        $resultado1 = $sql1->fetchAll();

        foreach ($resultado1 as $row) {
            
            $producto_codigo=$row['cp_producto_codigo_92939898234'];
            $categoria=$row['cp_categorias_nombre_92939898234'];
            $producto_descripcion=$row['cp_producto_descripcion_92939898234'];
            $inventario=$row['cp_inventario_matriz_cantidad_919819828828'];
            $producto_limite=$row['cp_producto_limite_92939898234'];
            
            if (is_numeric($producto_limite) and $producto_limite >= $inventario) {
                $responseTmp;
                $responseTmp["Cod"] = $producto_codigo;
                $responseTmp["categoria"] = $categoria;
                $responseTmp["producto_descripcion"] = $producto_descripcion;
                $responseTmp["inventario"] = $inventario;
                $responseTmp["limite"] = $producto_limite;
                array_push($response, $responseTmp);
                $responseSep = "+";
                array_push($response, $responseSep);
            } else {

            }

            
        }

        echo json_encode($response);
    }
    catch(PDOException $e) {
        echo "ERROR: " . $e->getMessage();
        die(json_encode($response));
    }
}
?>
