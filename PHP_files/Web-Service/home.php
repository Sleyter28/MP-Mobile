<?php 
$Utilidad = array();
$total_final=0;
if (!empty($_POST)) { 
    $username = "ReadApp"; 
    $password = "MarketPymesApp"; 
    $servername = "localhost";
    $dbname = $_POST['DB_name'];
    try {
        $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
        $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        $sql = $conn->prepare("select * from lp_factura_i092002992 where cp_fecha_99832423 BETWEEN :fecha_a and :fecha_b");
        $sql->execute(array('fecha_a' => $_POST['fecha'], 'fecha_b' => $_POST['fecha']));
        $resultado = $sql->fetchAll();

        foreach ($resultado as $row) {
            $id_fact=$row['id'];
            $p_nfact=$row['cp_numero_factura_919819828828'];
            $p_dia=$row['cp_dia_982394832432'];
            $p_mes=$row['cp_mes_983928234'];
            $p_anho=$row['cp_anho_09230943'];
            $id_facturas=$row['cp_id_factura_993284234'];
            $tf=$row['cp_tipo_factura_92384'];
            $id_caja=$row['cp_id_caja_993284234'];

            $sql2 = $conn->prepare("select 
            fc.cp_utilidad_position_99238434,
            fc.cp_cantidad_productos_9872384,
            ch.cp_producto_costo_historial_92939898234,
            uh.cp_producto_utilidad_1_historial_92939898234,
            uh.cp_producto_utilidad_2_historial_92939898234,
            uh.cp_producto_utilidad_3_historial_92939898234,
            im.cp_producto_impuesto_92939898234, 
            f.cp_id_company_919819828828,
            im.cp_id_producto_919819828828

            from 
            lp_factura_i092002992 f,
            lp_facturacion_i092002992 fc,
            lp_productos_codigo_i092002992 p,
            lp_producto_nombre_i092002992 pn,
            lp_productos_costos_historial_i092002992 ch,
            lp_productos_utilidades_historial_i092002992 uh,
            lp_productos_impuesto_i092002992 im
           
            where 
            f.id=:id and
            fc.cp_id_factura_993284234 =f.cp_id_factura_993284234 and
            p.cp_id_producto_919819828828 = fc.cp_id_producto_919819828828 and
            im.cp_id_producto_919819828828 = fc.cp_id_producto_919819828828 and
            pn.cp_id_producto_919819828828 = fc.cp_id_producto_919819828828 and
            ch.cp_id_costo_92389483984=fc.cp_id_costo_92389483984 and 
            uh.cp_id_utilidad_987823784234=fc.cp_id_utilidad_987823784234 and 
            f.cp_activar_factura_919819828828='2' and
            f.cp_tipo_factura_92384= :tipo ");
            $sql2->execute(array('id' => $id_fact=$row['id'], 'tipo' => $_POST['tipo']));
            $resultado2 = $sql2->fetchAll();

            foreach ($resultado2 as $row) {
              $cantidad_producto=$row['cp_cantidad_productos_9872384'];
              $costo_producto=$row['cp_producto_costo_historial_92939898234'];
              $utilidad_producto_1=$row['cp_producto_utilidad_1_historial_92939898234'];
              $utilidad_producto_2=$row['cp_producto_utilidad_2_historial_92939898234'];
              $utilidad_producto_3=$row['cp_producto_utilidad_3_historial_92939898234'];
              $position_producto=$row['cp_utilidad_position_99238434'];
              $impuesto_producto=$row['cp_producto_impuesto_92939898234'];
              $id_company=$row['cp_id_company_919819828828'];
              $id_pp=$row['cp_id_producto_919819828828'];

              $sql3 = $conn->prepare("select cp_impuesto_92939898234 from lp_pais_i_v_a_i092002992 where cp_id_company_919819828828 = :id_company");
              $sql3->execute(array('id_company' => $id_company));
              $resultado3 = $sql3->fetchAll();
              $impuesto= "";
              foreach ($resultado3 as $row) {
                $impuesto=$row['cp_impuesto_92939898234']; 
              }
            
              if($position_producto==4){
                $sql_p="select * from   lp_facturacion_utilidad_4_9823984324 
                      where  cp_id_producto_919819828828= :id_pp and cp_id_factura_993284234='$id_fac' order by id asc";
                $res_p=mysql_query($sql_p);
                $row_p = mysql_fetch_array($res_p);     
                $utilidad_producto_4n=$row_p['cp_producto_utilidad_4_historial_92939898233'];
                }
                  
                if($position_producto==1){
                    $porcentaje=$utilidad_producto_1/100;
                } else if($position_producto==2){
                    $porcentaje=$utilidad_producto_2/100;
                } else if($position_producto==3){
                     $porcentaje=$utilidad_producto_3/100;
                } else if($position_producto==4){
                    $sql4 = $conn->prepare("select * from   lp_facturacion_utilidad_4_9823984324 
                                            where  cp_id_producto_919819828828= :id_pp and cp_id_factura_993284234='$id_fac' order by id asc");
                    $sql4->execute(array('id_pp' => $id_pp));
                    $resultado4 = $sql4->fetchAll();
                    $utilidad_producto_4n= "";
                    foreach ($resultado4 as $row) {
                    $utilidad_producto_4n=$row['cp_producto_utilidad_4_historial_92939898233']; 
                    }
                    $porcentaje=$utilidad_producto_4n/100;    
                }
                  
                $precio_producto=$costo_producto*$porcentaje;
                $precio_producto_final=$precio_producto+$costo_producto;
                  
                if($impuesto_producto==1){
                    $porcentaje_impuesto=$precio_producto_final*$impuesto;
                } else {
                    $porcentaje_impuesto=0;
                }
                  
                $total_producto_final_iv=$porcentaje_impuesto+$precio_producto_final;    
                $total_producto=$cantidad_producto*$total_producto_final_iv;
                $total_final=$total_final+$total_producto;
            }
            
        }
        echo json_encode($total_final);
    }
    catch(PDOException $e) {
        echo "ERROR: " . $e->getMessage();
        die(json_encode($response));
    }
}
?>
