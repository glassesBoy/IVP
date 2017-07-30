/**
 * GPS设备
 */
Ext.define('ivp.view.gpsdata.EquipmentView', {
    extend : 'ivp.desktop.core.Module',
    alias : 'widget.equipmentview',
    itemId : 'equipmentviewId',

    init : function() {
        this.launcher = {
            text : 'GPS设备管理',
            iconCls : 'icon-grid'
        };
    },
    createWindow : function() {
        var me = this;
        var desktop = this.app.getDesktop();
        var win = desktop.getWindow('equipmentviewId');
        if (!win) {

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

            win = desktop.createWindow({
                id : 'equipmentviewId',
                title : 'GPS设备管理',
                iconCls : 'icon-grid',
                animCollapse : true,
                constrainHeader : true,
                layout : 'border',

                height : 500,
                width : 900,
                items : [{
                    xtype : 'gridpanel',
                    region : 'center',
                    stripeRows : true,
                    columnLines : true,
                    store : gpsStore,
                    split : true,
                    columns : [{
                        xtype : 'gridcolumn',
                        dataIndex : 'eName',
                        width : 200,
                        text : 'GPS设备名称'

                    }, {
                        xtype : 'gridcolumn',
                        dataIndex : 'eCode',
                        width : 200,
                        text : '设备编号'

                    }, {
                        xtype : 'gridcolumn',
                        dataIndex : 'notes',
                        width : 200,
                        text : '说明'

                    }],
                    dockedItems : [{
                        xtype : 'pagingtoolbar',
                        store : gpsStore,
                        dock : 'bottom',
                        displayInfo : true
                    }, {
                        xtype : 'toolbar',
                        dock : 'top',
                        items : [{
                            text : '添加GPS设备',
                            handler : me.addEquipment
                        }, '-', {
                            text : '删除',
                            handler : me.deleteEquipment
                        }]
                    }],
                    listeners : {
                        itemdblclick : {
                            fn : me.onUpdateEquipment,
                            scope : me
                        }
                    }

                }]
            });
        }
        return win;
    },

    addEquipment : function(btn) {

        var grid = btn.up('grid');
        var gridStore = grid.getStore();
        var createWin = Ext.create('ivp.view.gpsdata.EquipmentForm', {
            gridStore : gridStore,
            title : '新建GPS设备',
            udt : false
        });
        createWin.show();

    },
    deleteEquipment : function(btn) {

        var grid = btn.up('grid');
        var record = grid.getSelectionModel().getSelection()[0];
        if (record) {
        } else {
            Ext.MessageBox.alert('', '您好像没有选择任何信息');
            return false
        }

        // console.log('==================record ', record);
        Ext.Msg.confirm("信息", "确定要删除吗？", function(button) {
            if (button == "no") {
                return false;
            }
            var gridStore = grid.getStore();

            var vtID = record.data.id;

            Ext.Ajax.request({
                clientValidation : true,
                url : 's/gpse/dlt',
                params : {
                    "id" : vtID
                },
                success : function(response, options) {
                    var resp = Ext.decode(response.responseText);
                    Ext.Msg.alert('成功', resp.message);
                    gridStore.load();

                },
                failure : function(response, options) {
                    var resp = Ext.decode(response.responseText);
                    Ext.Msg.alert('失败', resp.message);
                }

            });
        });
    },

    onUpdateEquipment : function(grid, record, index, eOpts) {

        var gridStore = grid.getStore();
        var updateWin = Ext.create('ivp.view.gpsdata.EquipmentForm', {
            gridStore : gridStore,
            title : '修改GPS设备',
            udt : true
        });
        updateWin.down('form').getForm().setValues(record.data);
        updateWin.show();

    }

});
