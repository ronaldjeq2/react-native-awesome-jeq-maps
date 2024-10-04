import { NativeSyntheticEvent, ViewStyle } from 'react-native';
export type MarkerClickEvent = NativeSyntheticEvent<{
    latitude: number;
    longitude: number;
    title: string;
}>;
export interface AwesomeJeqMapsViewProps {
    markerData: {
        latitude: number;
        longitude: number;
        title: string;
        description: string;
    }[];
    style?: ViewStyle;
    onMarkerClick?: (event: MarkerClickEvent) => void;
}
