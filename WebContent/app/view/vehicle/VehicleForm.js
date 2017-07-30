/**
 * 修改 车辆 弹窗
 */
Ext.define('ivp.view.vehicle.VehicleForm', {
    extend : 'Ext.window.Window',
    alias : 'widget.vehicleform',
    itemId : 'vehicleteamId',

    // title : '修改车队',
    modal : true,
    constrainHeader : true,

    // autoShow : true,
    height : 400,
    width : 500,
    layout : 'fit',

    initComponent : function() {
        var me = this;

        Ext.applyIf(me, {
            items : [{
                xtype : 'form',
                bodyPadding : 10,
                items : [{
                    xtype : 'textfield',
                    // allowBlank : false,
                    hidden : true,
                    name : 'id'
                }, {
                    xtype : 'textfield',
                    hidden : true,
                    name : 'teamID'
                }, {
                    xtype : 'textfield',
                    allowBlank : false,
                    fieldLabel : '车辆名称',
                    anchor : '90%',
                    emptyText : '请输入车辆名称',
                    name : 'vName'
                }, {
                    xtype : 'textfield',
                    emptyText : '请输入车牌号码',
                    allowBlank : false,
                    anchor : '90%',
                    fieldLabel : '车牌号码',
                    name : 'vCode'
                }, {
                    xtype : 'numberfield',
                    anchor : '90%',
                    allowBlank : false,
                    decimalPrecision : 5,
                    fieldLabel : '车辆长度',
                    emptyText : '请输入车辆长度',
                    name : 'vLength'
                }, {
                    xtype : 'numberfield',
                    anchor : '90%',
                    allowBlank : false,
                    decimalPrecision : 5,
                    fieldLabel : '车辆宽度',
                    emptyText : '请输入车辆宽度',
                    name : 'vWidth'
                }, {
                    xtype : 'textfield',
                    anchor : '90%',
                    // allowBlank : false,
                    fieldLabel : 'GPS设备距车头距离(cm)',
                    emptyText : 'GPS设备距车头距离(cm)',
                    name : 'aa'

                }, {
                    xtype : 'textfield',
                    anchor : '90%',
                    // allowBlank : false,
                    fieldLabel : 'GPS设备距车左侧距离(cm)',
                    emptyText : 'GPS设备距左侧距离(cm)',
                    name : 'bb'
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
        // 这个是记住 loginview 窗口
        var view = this;
        // 先找到 form这个控件, 从btn 控件开始向上查找
        var formCmp = btn.up('form');
        var form = formCmp.getForm();
        if (form.isValid()) {

        } else {
            Ext.MessageBox.alert('', '请检查必填项');
            return false;
        }

        var values = form.getValues();
        // 如果有id 说明是修改
        // 如果没有，那么说明是 新建
        var submitURL = 's/v/udt';
        if (values.id) {
            // submitURL = 's/r/udt';
        } else {
            submitURL = 's/v/add'
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
    }
});
