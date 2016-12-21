<?php 
$response = [];
$facturas = [];
$servicios = [];
$total_producto_final_iv = 0;
if (!empty($_POST)) { 
    $username = "ReadApp"; 
    $password = "MarketPymesApp"; 
    $servername = "localhost";
    $dbname = $_POST['DB_name'];
    try {
        $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
        $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        $sql1 = $conn->prepare("select c.id, c.cp_id_caja_993284234, c.cp_monto_i98129812 from lp_caja_i092002992 c where c.cp_activar_919819828828 = 0 and c.cp_id_company_919819828828 = :id_company order by id desc limit 1");
        $sql1->execute(array('id_company' => $_POST['id_company']));
        $resultado1 = $sql1->fetchAll();

        foreach ($resultado1 as $row) {
            $id=$row['id'];
            $id_caja=$row['cp_id_caja_993284234'];
            $monto=$row['cp_monto_i98129812'];

            $responseTmp1["monto"] = $monto;
            $responseTmp["caja"] = $responseTmp1;
            

            $sql2 = $conn->prepare("select f.id, f.cp_fecha_99832423, f.cp_numero_factura_919819828828, f.cp_tipo_factura_92384 from 
            lp_factura_i092002992 f where f.cp_id_caja_993284234 = :id_caja");
            $sql2->execute(array('id_caja' => $id_caja));
            $resultado2 = $sql2->fetchAll();

            $responseTmp2;
            foreach ($resultado2 as $row) {
                $id_factu = $row['id'];
                $fecha=$row['cp_fecha_99832423'];
                $numero_factura=$row['cp_numero_factura_919819828828'];
                $tipo_factura=$row['cp_tipo_factura_92384'];

                $responseTmp2["fecha"] = $fecha;
                $responseTmp2["#_factura"] = $numero_factura;
                $responseTmp2["tipo_factura"] = $tipo_factura;

                $sql3 = $conn->prepare("select  f.cp_activar_factura_919819828828, f.cp_id_factura_993284234,
                    fc.cp_id_producto_919819828828, fc.cp_id_cliente_919819828828, fc.cp_id_costo_92389483984, 
                    fc.cp_id_utilidad_987823784234, fc.cp_utilidad_position_99238434, fc.cp_cantidad_productos_9872384, 
                    ch.cp_producto_costo_historial_92939898234, uh.cp_producto_utilidad_1_historial_92939898234,
                    uh.cp_producto_utilidad_2_historial_92939898234,uh.cp_producto_utilidad_3_historial_92939898234,im.cp_producto_impuesto_92939898234,
                    lp.cp_impuesto_92939898234

                    from lp_factura_i092002992 f, lp_facturacion_i092002992 fc, lp_productos_codigo_i092002992 p, lp_producto_nombre_i092002992 pn, 
                    lp_productos_costos_historial_i092002992 ch,  lp_productos_utilidades_historial_i092002992 uh, lp_productos_impuesto_i092002992 im, 
                    lp_pais_i_v_a_i092002992 lp
                    
                    where f.id= :id_fact and fc.cp_id_factura_993284234 =f.cp_id_factura_993284234 and p.cp_id_producto_919819828828 =
                    fc.cp_id_producto_919819828828 and im.cp_id_producto_919819828828 = fc.cp_id_producto_919819828828 and pn.cp_id_producto_919819828828 = 
                    fc.cp_id_producto_919819828828 and ch.cp_id_costo_92389483984=fc.cp_id_costo_92389483984 and 
                    uh.cp_id_utilidad_987823784234=fc.cp_id_utilidad_987823784234 and f.cp_id_company_919819828828 = 
                    lp.cp_id_company_919819828828

                    order by fc.id desc");
                $sql3->execute(array('id_fact' => $id_factu));
                $resultado3 = $sql3->fetchAll();
                $total_final =0;
                foreach ($resultado3 as $row_b) {
                    $costo_producto=$row_b['cp_producto_costo_historial_92939898234'];
                    $utilidad_producto_1=$row_b['cp_producto_utilidad_1_historial_92939898234'];
                    $utilidad_producto_2=$row_b['cp_producto_utilidad_2_historial_92939898234'];
                    $utilidad_producto_3=$row_b['cp_producto_utilidad_3_historial_92939898234'];
                    $position_producto=$row_b['cp_utilidad_position_99238434'];
                    $impuesto_producto=$row_b['cp_producto_impuesto_92939898234'];
                    $cp_impuesto=$row_b['cp_impuesto_92939898234'];
                    $cantidad_producto=$row_b['cp_cantidad_productos_9872384'];

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
                        $porcentaje_impuesto=$precio_producto_final*$cp_impuesto;
                    } else {
                        $porcentaje_impuesto=0;
                    }

                    $total_producto_final_iv=$porcentaje_impuesto+$precio_producto_final;
                
                    $total_producto=$cantidad_producto*$total_producto_final_iv;
                    $total_final=$total_final+$total_producto;
                    }
                $responseTmp2["monto"] = $total_final;

                array_push($facturas, $responseTmp2);
            }
            $responseTmp["facturas"] = $facturas;

            $sql4 = $conn->prepare("select c.cp_numero_factura_919819828828 from lp_caja_historial_servicio_i092002992 c
            where cp_id_caja_993284234= :id_caja and  cp_id_company_919819828828 = :id_company");
            $sql4->execute(array('id_company' => $_POST['id_company'], 'id_caja' => $id_caja));
            $resultado4 = $sql4->fetchAll();

            $responseTmp3;
            foreach ($resultado4 as $row) {
                $numero_factura =  $row['cp_numero_factura_919819828828'];
                $responseTmp3["#_factura"] = $numero_factura;

                $sql5 = $conn->prepare("select fs.cp_id_factura_993284234, fs.cp_fecha_99832423, fs.cp_tipo_factura_92384
                 from lp_factura_servicio_i092002992 fs
                where cp_id_company_919819828828= :id_company and cp_numero_factura_919819828828= :id_factura");
                $sql5->execute(array('id_company' => $_POST['id_company'], 'id_factura' => $numero_factura));
                $resultado5 = $sql5 ->fetchAll();
                foreach ($resultado5 as $row) {
                    $fecha = $row['cp_fecha_99832423'];
                    $id_factura = $row['cp_id_factura_993284234'];
                    $tipo_factura = $row['cp_tipo_factura_92384'];
                }
                $responseTmp3["fecha"] = $fecha;
                $responseTmp3["tipo_factura"] = $tipo_factura;

                $total_servicio = 0;
                $sql6 = $conn->prepare("select fc.cp_precio_servicio_9448736485 from lp_factura_servicio_i092002992 f, lp_facturacion_servicio_i092002992 fc
                where f.cp_id_factura_993284234 = :id_factura and
                fc.cp_id_factura_993284234 =f.cp_id_factura_993284234 order by fc.id desc ");
                $sql6 ->execute(array('id_factura' => $id_factura));
                $resultado6 = $sql6 ->fetchAll();
                foreach ($resultado6 as $row) {
                    $precio_servicio = $row['cp_precio_servicio_9448736485'];
                    $total_servicio = $total_servicio + $precio_servicio;
                }

                $responseTmp3["monto"] = $total_servicio;
                array_push($servicios, $responseTmp3);
            }
            $responseTmp["Servicios"] = $servicios;

            /*
            $sql7 = $conn->prepare("select g.cp_monto_gasto_92939898234 from lp_gastos_company_9823984324 g where cp_id_company_919819828828= :id_company 
                and cp_id_caja_993284234= :id_caja");
            $sql7->execute(array('id_company' => $_POST['id_company'], 'id_caja' => $id_caja));
            $resultado7 = $sql7->fetchAll();

            $responseTmp4;
            $gastos = 0;
            foreach ($resultado7 as $row) {
                $monto_gasto=$row['cp_monto_gasto_92939898234'];
                $gastos = $gastos + $monto_gasto;
            }
            $responseTmp4["monto"] =  $monto_gasto;
            $responseTmp["gastos"] = $responseTmp4;*/

            $monto_gasto = 0;
            $responseTmp4["monto"] =  $monto_gasto;
            $responseTmp["gastos"] = $responseTmp4;

            $response = $responseTmp;
        }
        echo json_encode($response);
    }
    catch(PDOException $e) {
        echo "ERROR: " . $e->getMessage();
        die(json_encode($response));
    }
}
?>
