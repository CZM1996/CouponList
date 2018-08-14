package com.cj.couponlist;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 优惠券的Adapter基类
 */
public abstract class BaseCouponAdapter extends BaseRecyclerAdapter<BaseCoupon> {

    public static final int COUPON_PAGE_PRODUCT = 1; // 商详页
    public static final int COUPON_PAGE_CHOOSE = 2;  // 结算页
    public static final int COUPON_PAGE_LIST = 3;    // 优惠券列表页

    private static final int ITEM_COUPON_STATUS_OK = 1;// 可领
    private static final int ITEM_COUPON_STATUS_ALREADY_GET = 2;// 已领取不可再领


    public static final int STATUS_CAN_USE = 1; // 可使用
    public static final int STATUS_ALREADY_USE = 2; // 已使用
    public static final int STATUS_ALREADY_OVERDUE = 3; // 已过期

    public static final int STATUS_BG = -1; // app重绘背景用，非服务端返回

    private Activity context;
    private int page;
    protected String para, skuId, type;

    BaseCouponAdapter(Activity context, int page) {
        this.context = context;
        this.page = page;
    }

    private void initCommonView(ViewHolder holder, View convertView) {
        holder.ticket_mark = convertView.findViewById(R.id.ticket_mark);
        holder.ticket_value = convertView.findViewById(R.id.ticket_value);
        holder.ticket_type = convertView.findViewById(R.id.ticket_type);
        holder.ticket_pic = convertView.findViewById(R.id.ticket_pic);
        holder.ticket_time = convertView.findViewById(R.id.ticket_time);
        holder.ticket_description = convertView.findViewById(R.id.ticket_description);
        holder.ticket_action = convertView.findViewById(R.id.ticket_action);
    }

    public void bindCommonData(BaseCoupon coupon, ViewHolder holder) {
        // 通用字段, 设置控件value
        int ticketMarkVisibility = coupon.isHideRmbSymbol() ? View.GONE : View.VISIBLE;
        holder.ticket_mark.setVisibility(ticketMarkVisibility);
        holder.ticket_value.setText(coupon.getDiscount());
        holder.ticket_type.setText(coupon.getTitle());

        if (StringUtils.isNotEmpty(coupon.getUsableTime())) {
            holder.ticket_time.setText(coupon.getUsableTime());
        }
        // 字体默认处理,特殊需要可以覆写
        if (coupon.getDiscount().length() >= 4) {
            float size = 125 / (coupon.getDiscount().length());
            holder.ticket_mark.setTextSize(size * 5 / 8);
            holder.ticket_value.setTextSize(size);
        } else {
            holder.ticket_mark.setTextSize(25);
            holder.ticket_value.setTextSize(40);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.item_coupon, parent, false);
        ViewHolder holder = new ViewHolder(root);
        // 初始化通用控件
        initCommonView(holder, root);
        // 初始化特别控件,在flavor apapter里
        initExtraView(holder, root);
        return holder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(View itemView) {
            super(itemView);
        }

        CouponDisplayView couponDisplayView;
        RelativeLayout ll_couponView;
        LinearLayout ll_top_part;
        TextView ticket_mark; // 人民币符号
        TextView ticket_value;//减多少
        TextView ticket_type;//优惠券类型
        ImageView ticket_pic;//有效性
        TextView ticket_time;//有效期
        TextView ticket_action;
        RelativeLayout rlt_discount_ticket_bottom_part;//item的下半部分
        TextView ticket_description;//优惠券使用说明
        ImageView ticket_point_iv;//箭头图片
        CheckBox ticket_select; // 优惠券选择
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int realPosition, BaseCoupon data) {
        // 绑定数据及事件
        bindData(realPosition, viewHolder, data);
    }

    private void bindData(int position, RecyclerView.ViewHolder holder, BaseCoupon data) {

        switch (page) {
            case COUPON_PAGE_LIST:
                type = para;
                dealForCouponList(position, (ViewHolder) holder, data);
                break;
            case COUPON_PAGE_PRODUCT:
                skuId = para;
                dealForProduct(position, (ViewHolder) holder, data);
                break;
            case COUPON_PAGE_CHOOSE:
                dealForCouponChoose(position, (ViewHolder) holder, data);
                break;
        }
    }

    private void dealForCouponChoose(int position, ViewHolder holder, BaseCoupon data) {
        // 通用控件绑定
        bindCommonData(data, holder);
        // flavor个性化数据绑定
        setData(data, holder);
        // flavor个性化绑定, 状态的绑定挪到个性化里
        setSelect(position, data, holder);
    }

    private void dealForCouponList(int position, ViewHolder holder, BaseCoupon data) {
        // 通用控件绑定
        bindCommonData(data, holder);

        //控制图片显示
        switch (Integer.parseInt(type)) {
            case STATUS_CAN_USE:
                // 动态设置背景
                setBg(holder, STATUS_CAN_USE);
                break;
            case STATUS_ALREADY_USE:
                // 动态设置浮标
                setPic(holder, STATUS_ALREADY_USE);
                // 动态设置背景
                setBg(holder, STATUS_ALREADY_USE);
                break;
            case STATUS_ALREADY_OVERDUE:
                // 动态设置浮标
                setPic(holder, STATUS_ALREADY_OVERDUE);
                // 动态设置背景
                setBg(holder, STATUS_ALREADY_OVERDUE);
                break;
            default:
                break;
        }

        // flavor个性化绑定
        setData(data, holder);
    }

    private void dealForProduct(int position, ViewHolder holder, BaseCoupon data) {
        // 通用控件绑定
        bindCommonData(data, holder);

        switch (data.getStatus()) {
            case ITEM_COUPON_STATUS_OK:
                holder.ticket_pic.setVisibility(View.GONE);
                // 动态设置背景
                setBg(holder, STATUS_CAN_USE);
                holder.ticket_action.setVisibility(View.VISIBLE);
                holder.ticket_action.setText("点击领取");
                holder.ticket_action.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击事件
                    }
                });
                break;
            case ITEM_COUPON_STATUS_ALREADY_GET:
                // 个性化处理
                setLimit(data, holder);
                holder.ticket_action.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        //领取
                    }
                });
                break;
            default:
                break;
        }
        setData(data, holder);
    }

    public abstract void initExtraView(ViewHolder holder, View convertView);

    public abstract void setBg(ViewHolder holder, int type);

    public abstract void setPic(ViewHolder holder, int type);

    public abstract void setData(BaseCoupon coupon, ViewHolder holder);

    public abstract void setSelect(int position, BaseCoupon coupon, ViewHolder holder);

    public abstract void setLimit(BaseCoupon coupon, ViewHolder holder);

}
