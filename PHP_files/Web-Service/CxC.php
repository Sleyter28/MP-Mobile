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
        $sql1 = $conn->prepare("select a.cp_id_cliente_919819828828, a.cp_cliente_nombre_92939898234, b.cp_cliente_codigo_92939898234 from lp_clientes_nombre_i092002992 a, lp_clientes_codigo_i092002992 b where ((a.cp_cliente_nombre_92939898234 LIKE :valor or b.cp_cliente_codigo_92939898234 LIKE :valor) and (b.cp_id_cliente_919819828828=a.cp_id_cliente_919819828828))");
        $sql1->execute(array('valor' => $_POST['valor']));
        $resultado1 = $sql1->fetchAll();

        foreach ($resultado1 as $row) {
            $id_cliente=$row['cp_id_cliente_919819828828'];
            $cp_cliente_nombre=$row['cp_cliente_nombre_92939898234'];
            $cp_cliente_codigo=$row['cp_cliente_codigo_92939898234'];

            $sql2 = $conn->prepare("select cp_id_factura_993284234, cp_cantidad_meses_098982222, cp_fechas_98283748234, cp_fechas_pago_98238723487 from lp_creditos_99829383 where cp_id_cliente_919819828828= :d_cliente and cp_id_company_919819828828 = :id_company order by cp_fechas_pago_98238723487 asc");
            $sql2->execute(array('d_cliente' => $id_cliente, 'id_company' => $_POST['id_company']));
            $resultado2 = $sql2->fetchAll();

            foreach ($resultado2 as $row) {
                $id_factura=$row['cp_id_factura_993284234'];
                $cp_cantidad_meses=$row['cp_cantidad_meses_098982222'];
                $cp_fechas=$row['cp_fechas_98283748234'];
                $cp_fechas_pago=$row['cp_fechas_pago_98238723487'];

                $sql3 = $conn->prepare("select cp_monto_98237487234 from lp_abonos_9928398432 where cp_numero_factura_9829384= :id_factura");
                $sql3->execute(array('id_factura' => $id_factura));
                $resultado3 = $sql3->fetchAll();

                $monto_abonado=0;
                $abono_monto_total=0;
                foreach ($resultado3 as $row) {
                    $monto_abonado=$row['cp_monto_98237487234'];
                    $abono_monto_total=$abono_monto_total+$monto_abonado;    
                }
                $total_abono = number_format($abono_monto_total, 2);

                $sql4 = $conn->prepare("select id from lp_factura_i092002992 where cp_numero_factura_919819828828 = :n_factura");
                $sql4->execute(array('n_factura' => $id_factura));
                $resultado4 = $sql4->fetchAll();

                $id_factu = 0;
                foreach ($resultado4 as $row) {
                    $id_factu=$row['id'];
                }

                $sql5 = $conn->prepare("select f.id, f.cp_id_company_919819828828, f.cp_activar_factura_919819828828, f.cp_id_factura_993284234,
                    f.cp_tipo_factura_92384,  fc.cp_id_producto_919819828828, fc.cp_id_cliente_919819828828, fc.cp_id_costo_92389483984, 
                    fc.cp_id_utilidad_987823784234, fc.cp_utilidad_position_99238434, fc.cp_cantidad_productos_9872384, p.cp_producto_codigo_92939898234,
                    ch.cp_producto_costo_historial_92939898234, uh.cp_producto_utilidad_1_historial_92939898234,
                    uh.cp_producto_utilidad_2_historial_92939898234,uh.cp_producto_utilidad_3_historial_92939898234,im.cp_producto_impuesto_92939898234,
                    lp.cp_impuesto_92939898234

                    from lp_factura_i092002992 f, lp_facturacion_i092002992 fc, lp_productos_codigo_i092002992 p, lp_producto_nombre_i092002992 pn, 
                    lp_productos_costos_historial_i092002992 ch,  lp_productos_utilidades_historial_i092002992 uh, lp_productos_impuesto_i092002992 im, 
                    lp_pais_i_v_a_i092002992 lp
                    
                    where f.id= :id_fact and fc.cp_id_factura_993284234 =f.cp_id_factura_993284234 and p.cp_id_producto_919819828828 =
                    fc.cp_id_producto_919819828828 and im.cp_id_producto_919819828828 = fc.cp_id_producto_919819828828 and pn.cp_id_producto_919819828828 = 
                    fc.cp_id_producto_919819828828 and ch.cp_id_costo_92389483984=fc.cp_id_costo_92389483984 and 
                    uh.cp_id_utilidad_987823784234=fc.cp_id_utilidad_987823784234 and f.cp_tipo_factura_92384 = 2 and f.cp_id_company_919819828828 = 
                    lp.cp_id_company_919819828828

                    order by fc.id desc");
                $sql5->execute(array('id_fact' => $id_factu));
                $resultado5 = $sql5->fetchAll();

                $total_final =0;
                $cuota =0;
                $saldo =0;
                foreach ($resultado5 as $row_b) {
                    $id_producto=$row_b['cp_producto_codigo_92939898234'];
                    $id_p=$row_b['id'];
                    //Precio
                    $costo_producto=$row_b['cp_producto_costo_historial_92939898234'];
                    $utilidad_producto_1=$row_b['cp_producto_utilidad_1_historial_92939898234'];
                    $utilidad_producto_2=$row_b['cp_producto_utilidad_2_historial_92939898234'];
                    $utilidad_producto_3=$row_b['cp_producto_utilidad_3_historial_92939898234'];
                    $position_producto=$row_b['cp_utilidad_position_99238434'];
                    $impuesto_producto=$row_b['cp_producto_impuesto_92939898234'];
                    $tipo_facturas=$row_b['cp_tipo_factura_92384'];
                    $cp_id_company=$row_b['cp_id_company_919819828828'];
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
                $cuota = $total_final/$cp_cantidad_meses;
    
                $saldo = $total_final - $abono_monto_total;
                $saldo = number_format($saldo, 2);
                      
                $total_final = number_format($total_final, 2);
                $cuota = number_format($cuota, 2);

                $responseTmp;
                $responseTmp["id_factura"] = $id_factura;
                $responseTmp["fechas_pago"] = $cp_fechas_pago;
                $responseTmp["cliente"] = $cp_cliente_nombre;
                $responseTmp["deuda"] = $total_final;
                $responseTmp["cuota"] = $cuota;
                $responseTmp["abonado"] = $total_abono;
                $responseTmp["saldo"] = $saldo;
                array_push($response, $responseTmp);
                $responseSep = "+";
                array_push($response, $responseSep);
            }
        }
        echo json_encode($response);
    }
    catch(PDOException $e) {
        echo "ERROR: " . $e->getMessage();
        die(json_encode($id_cliente));
    }
}
?>
