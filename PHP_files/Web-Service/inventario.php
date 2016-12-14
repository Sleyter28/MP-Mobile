<?php 
$response = [];
$total_producto_final_iv = 0;
if (!empty($_POST)) { 
    $username = "ReadApp"; 
    $password = "MarketPymesApp"; 
    $servername = "localhost";
    $dbname = $_POST['DB_name'];
    try {
        $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
        $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        $sql1 = $conn->prepare("Select a.cp_id_producto_919819828828, a.cp_id_company_919819828828, b.cp_producto_nombre_92939898234,
        d.cp_categorias_nombre_92939898234, f.cp_producto_descripcion_92939898234, a.cp_id_producto_index_919819828828, g.cp_id_categoria_919819828828,
        i.cp_inventario_matriz_cantidad_919819828828
        from 
        lp_productos_i092002992 a
        INNER JOIN  lp_producto_nombre_i092002992 b
        INNER JOIN  lp_productos_codigo_i092002992 c
        INNER JOIN  lp_categorias_nombre_i092002992 d
        INNER JOIN  lp_producto_descripcion_i092002992 f
        INNER JOIN  lp_productos_categoria_i092002992 g
        INNER JOIN lp_productos_codigo_barras_i092002992 h
        INNER JOIN lp_inventario_matriz_i092002992 i
        ON b.cp_id_producto_919819828828=a.cp_id_producto_index_919819828828 and
        c.cp_id_producto_919819828828=a.cp_id_producto_index_919819828828 and 
        f.cp_id_producto_919819828828=a.cp_id_producto_index_919819828828 and 
        g.cp_id_producto_919819828828=a.cp_id_producto_index_919819828828 and
        h.cp_id_producto_919819828828=a.cp_id_producto_index_919819828828 and
        i.cp_id_producto_919819828828 = a.cp_id_producto_index_919819828828
        where 
        ((b.cp_producto_nombre_92939898234 LIKE :valor or c.cp_producto_codigo_92939898234 LIKE :valor or f.cp_producto_descripcion_92939898234 LIKE :valor ) 
        and (d.cp_id_categoria_919819828828=g.cp_id_categoria_919819828828 and a.cp_activo_92982992='1' ))");
        $sql1->execute(array('valor' => $_POST['valor']));
        $resultado1 = $sql1->fetchAll();

        foreach ($resultado1 as $row) {
            
            $cp_id_producto=$row['cp_id_producto_919819828828'];
            $cp_producto_nombre=$row['cp_producto_nombre_92939898234'];
            $cp_categorias_nombre=$row['cp_categorias_nombre_92939898234'];
            $cp_producto_descripcion=$row['cp_producto_descripcion_92939898234'];
            $cp_id_company=$row['cp_id_company_919819828828'];
            $inventario=$row['cp_inventario_matriz_cantidad_919819828828'];
            $id_producto=$row['cp_id_producto_index_919819828828'];
            $id_categoria=$row['cp_id_categoria_919819828828'];


            $sql2 = $conn->prepare("select ch.cp_producto_costo_historial_92939898234, uh.cp_producto_utilidad_1_historial_92939898234, 
            uh.cp_producto_utilidad_2_historial_92939898234, uh.cp_producto_utilidad_3_historial_92939898234
            from 
            lp_productos_codigo_i092002992 p, lp_productos_costos_historial_i092002992 ch, lp_productos_utilidades_historial_i092002992 uh
            where 
            p.cp_id_producto_919819828828= :cp_id_producto and 
            ch.cp_id_producto_919819828828 = p.cp_id_producto_919819828828 and
            uh.cp_id_producto_919819828828 = p.cp_id_producto_919819828828
            order by ch.id desc, uh.id desc limit 1");
            $sql2->execute(array('cp_id_producto' => $cp_id_producto));
            $resultado2 = $sql2->fetchAll();

            foreach ($resultado2 as $row) {
                $costo_producto=$row['cp_producto_costo_historial_92939898234'];
                $utilidad_producto_1=$row['cp_producto_utilidad_1_historial_92939898234'];
                $utilidad_producto_2=$row['cp_producto_utilidad_2_historial_92939898234'];
                $utilidad_producto_3=$row['cp_producto_utilidad_3_historial_92939898234'];
                $position_producto=1;
            }

            $sql3 = $conn->prepare("select cp_producto_impuesto_92939898234 from lp_productos_impuesto_i092002992 
            where cp_id_producto_919819828828 = :cp_id_producto");
            $sql3->execute(array('cp_id_producto' => $cp_id_producto));
            $resultado3 = $sql3->fetchAll();

             $cp_producto_impuesto=0;
            foreach ($resultado3 as $row) {
                $impuesto_producto=$row['cp_producto_impuesto_92939898234'];
            }


            $sql4 = $conn->prepare("select cp_impuesto_92939898234 from lp_pais_i_v_a_i092002992 where cp_id_company_919819828828 = :cp_id_company");
            $sql4->execute(array('cp_id_company' => $cp_id_company));
            $resultado4 = $sql4->fetchAll();

            $impuesto = 0;
            foreach ($resultado4 as $row) {
                $impuesto=$row['cp_impuesto_92939898234'];
            }

            $porcentaje = 0;
            if($position_producto==1){
                $porcentaje=$utilidad_producto_1/100;
            } else if($position_producto==2){
                $porcentaje=$utilidad_producto_2/100;
            } else if($position_producto==3){
                $porcentaje=$utilidad_producto_3/100;
            }

            $precio_producto=$costo_producto*$porcentaje;
            $precio_producto_final=$precio_producto+$costo_producto;

            if($impuesto_producto==1){
                $porcentaje_impuesto=$precio_producto_final*$impuesto;
            } else {
                $porcentaje_impuesto=0;
            }

            $total_producto_final_iv=$porcentaje_impuesto+$precio_producto_final;
            $total_producto_final_iv = number_format($total_producto_final_iv, 2);

            $responseTmp;
            $responseTmp["producto_nombre"] = $cp_producto_nombre;
            $responseTmp["categoria"] = $cp_categorias_nombre;
            $responseTmp["producto_descripcion"] = $cp_producto_descripcion;
            $responseTmp["precio"] = $total_producto_final_iv;
            $responseTmp["inventario"] = $inventario;
            array_push($response, $responseTmp);
            }

        echo json_encode($response);
    }
    catch(PDOException $e) {
        echo "ERROR: " . $e->getMessage();
        die(json_encode($response));
    }
}
?>
