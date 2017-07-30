/**
 * 修改 测试车辆 弹窗
 */
Ext.define('ivp.view.gpsdata.TestingVehicleForm', {
    extend : 'Ext.window.Window',
    alias : 'widget.testingvehicleform',
    itemId : 'testingvehicleformId',

    // title : ' ',
    modal : true,
    constrainHeader : true,

    // autoShow : true,
    height : 500,
    width : 700,
    layout : 'border',

    initComponent : function() {
        var me = this;

        var vStore = Ext.create('Ext.data.Store', {
            // model : 'ivp.model.VehicleModel',
            fields : [{
                name : 'id',
                type : 'auto'
            }, {
                name : 'vName',
                type : 'auto'// 
            }, {
                name : 'vCode'
            }, {
                name : 'teamName'
            }, {
                name : 'vLength'
            }, {
                name : 'vWidth'
            }, {
                name : 'vHeight'
            }],
            autoLoad : true,
            proxy : {
                type : 'ajax',
                url : 's/v/all',
                reader : {
                    type : "json",
                    root : "data",
                    successProperty : 'success',
                    totalProperty : 'total'
                }
            }
        });

        var gpsStore = Ext.create('Ext.data.Store', {
            fields : ['id', 'eName', 'eCode', 'notes'],
            autoLoad : true,
            proxy : {
                type : 'ajax',
                url : 's/gpse/s',
                reader : {
                    type : "json",
                    root : "data",
                    successProperty : 'success',
                    totalProperty : 'total'
                }
            }
        });

        var obstacleStore = Ext.create('Ext.data.Store', {
            fields : ['id', 'oName', 'x', 'y', 'notes'],
            autoLoad : true,
            proxy : {
                type : 'ajax',
                url : 's/obtl/s',
                reader : {
                    type : "json",
                    root : "data",
                    successProperty : 'success',
                    totalProperty : 'total'
                }
            }
        });

        Ext.applyIf(me, {
            items : [{
                xtype : 'form',
                region : 'center',
                autoScroll : true,
                bodyPadding : 10,
                title : '车辆信息',
                items : [{
                    xtype : 'textfield',
                    hidden : true,
                    name : 'id'
                }, {
                    xtype : 'fieldset',
                    height : 240,
                    // autoScroll : true,
                    title : '车辆',
                    items : [{
                        xtype : 'textfield',
                        hidden : true,
                        name : 'vid'
                    }, {
                        xtype : 'textfield',
                        fieldLabel : '车辆名称',
                        anchor : '90%',
                        readOnly : true,
                        emptyText : '请输入车辆名称',
                        name : 'vName'
                    }, {
                        xtype : 'textfield',
                        emptyText : '请输入车牌号码',
                        anchor : '90%',
                        readOnly : true,
                        fieldLabel : '车牌号码',
                        name : 'vCode'
                    }, {
                        xtype : 'numberfield',
                        anchor : '90%',
                        decimalPrecision : 5,
                        readOnly : true,
                        fieldLabel : '车辆长度',
                        emptyText : '请输入车辆长度',
                        name : 'vLength'
                    }, {
                        xtype : 'numberfield',
                        anchor : '90%',
                        readOnly : true,
                        decimalPrecision : 5,
                        fieldLabel : '车辆宽度',
                        emptyText : '请输入车辆宽度',
                        name : 'vWidth'
                    }, {
                        xtype : 'textfield',
                        anchor : '90%',
                        readOnly : true,
                        fieldLabel : 'GPS设备距车头距离(cm)',
                        emptyText : 'GPS设备距车头距离(cm)',
                        name : 'aa'
                    }, {
                        xtype : 'textfield',
                        anchor : '90%',
                        readOnly : true,
                        fieldLabel : 'GPS设备距车左侧距离(cm)',
                        emptyText : 'GPS设备距左侧距离(cm)',
                        name : 'bb'
                    }],

                    listeners : {
                        render : me.initializeDropVehicle
                    }

                }, {
                    xtype : 'fieldset',
                    height : 80,
                    title : '绑定GPS设备',
                    items : [{
                        xtype : 'textfield',
                        hidden : true,
                        name : 'gpseid'
                    }, {
                        xtype : 'textfield',
                        anchor : '90%',
                        readOnly : true,
                        fieldLabel : '设备名称',
                        name : 'eName'
                    }, {
                        xtype : 'textfield',
                        anchor : '90%',
                        readOnly : true,
                        fieldLabel : '设备编号',
                        // emptyText : '请输入车辆宽度',
                        name : 'eCode'
                    }],
                    listeners : {
                        render : me.initializeDropGPS
                    }

                }, {
                    xtype : 'fieldset',
                    height : 120,
                    title : '障碍物',
                    items : [{
                        xtype : 'textfield',
                        // allowBlank : false,
                        hidden : true,
                        name : 'oid'
                    }, {
                        xtype : 'textfield',
                        anchor : '90%',
                        readOnly : true,
                        fieldLabel : '障碍物名称',
                        name : 'oName'
                    }, {
                        xtype : 'numberfield',
                        anchor : '90%',
                        readOnly : true,
                        decimalPrecision : 8,
                        fieldLabel : '经度',
                        name : 'x'
                    }, {
                        xtype : 'numberfield',
                        anchor : '90%',
                        readOnly : true,
                        decimalPrecision : 8,
                        fieldLabel : '纬度',
                        name : 'y'

                    }],
                    listeners : {
                        render : me.initializeDropObstacle
                    }

                }]
            }, {
                xtype : 'panel',
                region : 'west',
                width : 240,
                // layout : 'border',
                layout : 'accordion',
                split : true,
                // title : 'My Panel',
                items : [{
                    xtype : 'gridpanel',
                    title : '车辆信息',
                    // height : 180,
                    // region : 'north',
                    store : vStore,
                    viewConfig : {
                        listeners : {
                            render : me.initializeDragVehicle
                        }
                    },
                    enableDragDrop : true,
                    columns : [{
                        xtype : 'gridcolumn',
                        dataIndex : 'vName',
                        text : '车辆名称'
                    }, {
                        xtype : 'gridcolumn',
                        dataIndex : 'vCode',
                        text : '车牌号码'
                    }]
                }, {
                    xtype : 'gridpanel',
                    title : 'GPS设备信息',
                    store : gpsStore,
                    // region : 'center',
                    // height : 180,
                    viewConfig : {
                        listeners : {
                            render : me.initializeDragGPS
                        }
                    },
                    columns : [{
                        xtype : 'gridcolumn',
                        dataIndex : 'eName',
                        text : 'GPS设备名称'
                    }, {
                        xtype : 'gridcolumn',
                        dataIndex : 'eCode',
                        text : '设备编号'
                    }]
                }, {
                    xtype : 'gridpanel',
                    title : '障碍物信息',
                    store : obstacleStore,
                    // height : 180,
                    // region : 'south',
                    viewConfig : {
                        listeners : {
                            render : me.initializeDragObstacle
                        }
                    },
                    columns : [{
                        xtype : 'gridcolumn',
                        dataIndex : 'oName',
                        text : '障碍物名称'
                    }, {
                        xtype : 'gridcolumn',
                        dataIndex : 'x',
                        text : '经度'
                    }, {
                        xtype : 'gridcolumn',
                        dataIndex : 'y',
                        text : '纬度'
                    }]
                }]
            }],
            buttons : [{
                xtype : 'button',
                text : '保存',
                listeners : {
                    click : {
                        fn : me.onSubmit,
                        scope : me
                    }
                }

            }, {
                xtype : 'button',
                text : '关闭',
                listeners : {
                    click : {
                        fn : me.onCancelButtonClick,
                        scope : me
                    }
                }

            }]
        });

        me.callParent(arguments);

    },

    // 关闭按钮
    onCancelButtonClick : function(btn) {
        btn.up('window').close();
    },

    /**
     * 提交
     */
    onSubmit : function(btn, e, eOpts) {
        var view = this;
        // 先找到 form这个控件, 从btn 控件开始向上查找
        var formCmp = btn.up('window').down('form');
        var form = formCmp.getForm();
        if (form.isValid()) {

        } else {
            Ext.MessageBox.alert('', '请检查必填项');
            return false;
        }

        var values = form.getValues();
        // 如果有id 说明是修改
        // 如果没有，那么说明是 新建
        var submitURL = 's/tstv/udt';
        if (values.id) {
        } else {
            submitURL = 's/tstv/add'
        }

        form.submit({
            clientValidation : true,
            url : submitURL,
            waitMsg : "正在保存，请稍后...",
            success : function(form, action) {
                // console.log('=========== success action ', action);
                Ext.Msg.alert('成功', action.result.message);

                view.close();
                view.gridStore.load();

            },
            failure : function(form, action) {
                // console.log('===========action ', action);
                Ext.Msg.alert('失败', action.result.message);
            }
        });
    },

    initializeDropVehicle : function(v) {
        var fset = v;
        fset.dropZone = Ext.create('Ext.dd.DropZone', v.el, {
            getTargetFromEvent : function(e) {
                // return e.getTarget(myGridPanel.getView().rowSelector);
                return true;
            },

            onNodeEnter : function(target, dd, e, data) {
                if (data.vehicle) {
                    return Ext.dd.DropZone.prototype.dropAllowed;
                } else {
                    return Ext.dd.DropZone.prototype.dropNotAllowed;
                }
            },

            onNodeOut : function(target, dd, e, data) {
                if (data.vehicle) {
                    return Ext.dd.DropZone.prototype.dropAllowed;
                } else {
                    return Ext.dd.DropZone.prototype.dropNotAllowed;
                }
            },

            onNodeOver : function(target, dd, e, data) {
                if (data.vehicle) {
                    return Ext.dd.DropZone.prototype.dropAllowed;
                } else {
                    return Ext.dd.DropZone.prototype.dropNotAllowed;
                }
            },

            onNodeDrop : function(target, dd, e, data) {
                if (data.vehicle) {
                    fset.up('form').getForm().setValues({
                        'vid' : data.vehicleData.id,
                        'vCode' : data.vehicleData.vCode,
                        'vHeight' : data.vehicleData.vHeight,
                        'vLength' : data.vehicleData.vLength,
                        'vName' : data.vehicleData.vName,
                        'vWidth' : data.vehicleData.vWidth
                    });
                    return true;
                } else {
                    return false;
                }
            }
        });
    },

    initializeDragVehicle : function(v) {
        v.dragZone = Ext.create('Ext.dd.DragZone', v.getEl(), {
            getDragData : function(e) {
                var sourceEl = e.getTarget(v.itemSelector, 10), d;
                if (sourceEl) {
                    d = sourceEl.cloneNode(true);
                    d.id = Ext.id();
                    return (v.dragData = {
                        sourceEl : sourceEl,
                        repairXY : Ext.fly(sourceEl).getXY(),
                        ddel : d,
                        vehicle : true,
                        vehicleData : v.getRecord(sourceEl).data
                    });
                }
            },
            getRepairXY : function() {
                return this.dragData.repairXY;
            }
        });
    },

    initializeDragGPS : function(v) {
        v.dragZone = Ext.create('Ext.dd.DragZone', v.getEl(), {
            getDragData : function(e) {
                var sourceEl = e.getTarget(v.itemSelector, 10), d;
                if (sourceEl) {
                    d = sourceEl.cloneNode(true);
                    d.id = Ext.id();
                    return (v.dragData = {
                        sourceEl : sourceEl,
                        repairXY : Ext.fly(sourceEl).getXY(),
                        ddel : d,
                        gpse : true,
                        gpseData : v.getRecord(sourceEl).data
                    });
                }
            },
            getRepairXY : function() {
                return this.dragData.repairXY;
            }
        });
    },

    initializeDropGPS : function(v) {
        var fset = v;
        fset.dropZone = Ext.create('Ext.dd.DropZone', v.el, {
            getTargetFromEvent : function(e) {
                // return e.getTarget(myGridPanel.getView().rowSelector);
                return true;
            },

            onNodeEnter : function(target, dd, e, data) {
                if (data.gpse) {
                    return Ext.dd.DropZone.prototype.dropAllowed;
                } else {
                    return Ext.dd.DropZone.prototype.dropNotAllowed;
                }
            },

            onNodeOut : function(target, dd, e, data) {
                if (data.gpse) {
                    return Ext.dd.DropZone.prototype.dropAllowed;
                } else {
                    return Ext.dd.DropZone.prototype.dropNotAllowed;
                }
            },

            onNodeOver : function(target, dd, e, data) {
                if (data.gpse) {
                    return Ext.dd.DropZone.prototype.dropAllowed;
                } else {
                    return Ext.dd.DropZone.prototype.dropNotAllowed;
                }
            },

            onNodeDrop : function(target, dd, e, data) {
                if (data.gpse) {
                    fset.up('form').getForm().setValues({
                        'gpseid' : data.gpseData.id,
                        'eName' : data.gpseData.eName,
                        'eCode' : data.gpseData.eCode
                    });
                    return true;
                } else {
                    return false;
                }
            }
        });
    },

    initializeDragObstacle : function(v) {
        v.dragZone = Ext.create('Ext.dd.DragZone', v.getEl(), {
            getDragData : function(e) {
                var sourceEl = e.getTarget(v.itemSelector, 10), d;
                if (sourceEl) {
                    d = sourceEl.cloneNode(true);
                    d.id = Ext.id();
                    return (v.dragData = {
                        sourceEl : sourceEl,
                        repairXY : Ext.fly(sourceEl).getXY(),
                        ddel : d,
                        obstacle : true,
                        obstacleData : v.getRecord(sourceEl).data
                    });
                }
            },
            getRepairXY : function() {
                return this.dragData.repairXY;
            }
        });
    },
    initializeDropObstacle : function(v) {
        var fset = v;
        fset.dropZone = Ext.create('Ext.dd.DropZone', v.el, {
            getTargetFromEvent : function(e) {
                // return e.getTarget(myGridPanel.getView().rowSelector);
                return true;
            },

            onNodeEnter : function(target, dd, e, data) {
                if (data.obstacle) {
                    return Ext.dd.DropZone.prototype.dropAllowed;
                } else {
                    return Ext.dd.DropZone.prototype.dropNotAllowed;
                }
            },

            onNodeOut : function(target, dd, e, data) {
                if (data.obstacle) {
                    return Ext.dd.DropZone.prototype.dropAllowed;
                } else {
                    return Ext.dd.DropZone.prototype.dropNotAllowed;
                }
            },

            onNodeOver : function(target, dd, e, data) {
                if (data.obstacle) {
                    return Ext.dd.DropZone.prototype.dropAllowed;
                } else {
                    return Ext.dd.DropZone.prototype.dropNotAllowed;
                }
            },

            onNodeDrop : function(target, dd, e, data) {
                if (data.obstacle) {
                    fset.up('form').getForm().setValues({
                        'oid' : data.obstacleData.id,
                        'oName' : data.obstacleData.oName,
                        'x' : data.obstacleData.x,
                        'y' : data.obstacleData.y
                    });
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

});
